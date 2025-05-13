# FlexCart Backend - Campaign Management

This project is part of the **FlexCart Backend**, designed to provide a flexible and extensible campaign management system. The system allows for the creation of campaigns with customizable **Conditions** and **Actions**, enabling businesses to define dynamic promotional rules and discounts. Crucially, the examples below align **exactly** with the campaign scenarios defined in `case.pdf`, and demonstrate how the same JSON structures map to our code’s flexible model—allowing even far more complex campaigns to be declared without changing a line of Java.

---

## Entity Relationship Diagram

Below is the Entity Relationship Diagram (ERD) for the FlexCart Backend:

![Entity Relationship Diagram](image/ER%20Diagram.png)

---

## Features

- **Flexible Campaign Structure**: Campaigns are built using a combination of `Condition` and `Action` classes, making it easy to define complex promotional rules.
- **Dynamic Conditions**: Conditions determine whether a campaign is applicable to a given order. Examples include:
  - Minimum total purchase amount.
  - Specific day of the week.
  - User type (e.g., premium users).
  - Purchase count (e.g., nth purchase).
- **Customizable Actions**: Actions define what happens when a campaign is applied. Examples include:
  - Percentage discounts on total or specific categories/products.
  - Fixed amount discounts.
  - Free shipping.
  - Free units of a product or category.

---

## Campaign Structure

A campaign consists of the following components:

### 1. **Conditions**

Conditions are rules that must be satisfied for a campaign to be applied. Each condition implements the `Condition` interface:

```java
public interface Condition {
    boolean isSatisfiedBy(Order order);
}
```

### 2. **Actions**

Actions define the benefits or discounts provided by the campaign. Each action implements the `Action` interface:

```java
public interface Action {
    BigDecimal apply(Order order);
}
```

#### Examples:

- **PercentageOnTotalAction**: Applies a percentage discount on the total order value.
- **FixedAmountAction**: Deducts a fixed amount from the total order value.
- **FreeShippingAction**: Provides free shipping for the order.
- **FreeUnitsOnCategoryAction**: Grants free units of a product or category.

---

## Campaign Creation Workflow

1. **Define Conditions**: Specify the rules under which the campaign will be applicable.
2. **Define Actions**: Specify the benefits or discounts to be applied when the campaign is triggered.
3. **Combine Conditions and Actions**: Use a JSON payload in your REST endpoint to define a campaign.

### Example JSON for Campaign Creation

Below are **exactly** the same JSON snippets from `case.pdf` that correspond to Free Shipping, Percentage Discount, Bundle, Date-Based Discount, and First Purchase scenarios. Our code maps each of these directly into `Campaign`, `CampaignConditionEntity`, and `CampaignActionEntity` using the `ConditionFactory` and `ActionFactory`. Because the model is JSON‐driven, you can go beyond these and author far more complex campaigns without altering the Java code.

#### Note: startDate and endDate parameters need to be in the future, or the application reponds http 400 bad request.

#### Free Shipping Campaign (min cart value)

```json
{
  "name": "Free Shipping Campaign",
  "description": "Free Shipping Campaign for min cart value",
  "type": "SHIPPING",
  "startDate": "2025-05-13T20:30:00",
  "endDate": "2025-06-31T12:00:00",
  "actions": [{ "type": "FREE_SHIPPING" }],
  "conditions": [
    {
      "type": "MIN_TOTAL",
      "parameters": { "minTotal": 1000 }
    }
  ]
}
```

#### Free Shipping Campaign (premium users)

```json
{
  "name": "Free Shipping Campaign",
  "description": "Free Shipping Campaign for premium users",
  "type": "SHIPPING",
  "startDate": "2025-05-13T20:30:00",
  "endDate": "2025-06-31T12:00:00",
  "actions": [{ "type": "FREE_SHIPPING" }],
  "conditions": [
    {
      "type": "USER_TYPE",
      "parameters": { "userType": "PREMIUM" }
    }
  ]
}
```

#### Percentage Discount on Electronics

```json
{
  "name": "Percentage Discount",
  "description": "Percentage discount on electronics.",
  "type": "PRICE",
  "startDate": "2025-05-13T20:30:00",
  "endDate": "2025-06-31T12:00:00",
  "actions": [
    {
      "type": "PERCENTAGE_ON_CATEGORY",
      "parameters": { "categoryId": 6, "percent": 10.0 }
    }
  ],
  "conditions": [
    { "type": "MIN_TOTAL", "parameters": { "minTotal": 2500 } },
    { "type": "USER_TYPE", "parameters": { "userType": "PREMIUM" } }
  ]
}
```

#### Bundle Offer on Books (Buy 2 Get 1)

```json
{
  "name": "Bundle",
  "description": "Buy 2 get 1 free on books.",
  "type": "PRICE",
  "startDate": "2025-05-13T20:30:00",
  "endDate": "2025-06-31T12:00:00",
  "actions": [
    {
      "type": "FREE_UNITS_ON_CATEGORY",
      "parameters": { "categoryId": 2, "freeUnits": 1 }
    }
  ],
  "conditions": [
    {
      "type": "CATEGORY_QUANTITY",
      "parameters": { "categoryId": 2, "quantity": 3 }
    }
  ]
}
```

#### Date-Based Discount (e.g., Tuesday Electronics)

```json
{
  "name": "Daily Promotion",
  "description": "Tuesday 5% off electronics.",
  "type": "PRICE",
  "startDate": "2025-05-13T20:30:00",
  "endDate": "2025-06-31T12:00:00",
  "actions": [
    {
      "type": "PERCENTAGE_ON_CATEGORY",
      "parameters": { "categoryId": 1, "percent": 5.0 }
    }
  ],
  "conditions": [
    {
      "type": "DAY_OF_WEEK",
      "parameters": { "dayOfWeek": "tuesday" }
    }
  ]
}
```

#### First Purchase Offer (25% + Free Shipping)

```json
{
  "name": "First Purchase Offer",
  "description": "25% discount and free shipping",
  "type": "PRICE",
  "startDate": "2025-05-13T20:30:00",
  "endDate": "2025-06-31T12:00:00",
  "actions": [
    { "type": "FREE_SHIPPING" },
    { "type": "PERCENTAGE_ON_TOTAL", "parameters": { "percent": 25.0 } }
  ],
  "conditions": [{ "type": "PURCHASE_COUNT", "parameters": { "nth": 1 } }]
}
```

---

#### Note: "type" parameter is used to set if Campaign is a PRICE or SHIPPING campaign. If shipping, the campaign added after the best possible PRICE campaign is applied.

## Extensibility

Thanks to our JSONB‐driven `CampaignConditionEntity` and `CampaignActionEntity`, plus the `ConditionFactory` and `ActionFactory` in code, you can support an **infinite** variety of new conditions and actions. Just add a new `Condition` or `Action` implementation, register it in the factory, and your JSON definitions—whether from the original `case.pdf` examples or brand‑new promotions—work without any further code changes.

---

## Conclusion

By aligning our README examples with those in `case.pdf`, we've demonstrated that the system meets the case’s requirements exactly. Moreover, the flexible architecture ensures that far more complex campaigns can be created on the fly—empowering marketing teams to iterate quickly without deploying new code.
