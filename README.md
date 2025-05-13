# FlexCart Backend - Campaign Management

This project is part of the **FlexCart Backend**, designed to provide a flexible and extensible campaign management system. The system allows for the creation of campaigns with customizable **Conditions** and **Actions**, enabling businesses to define dynamic promotional rules and discounts.

---

## Entity Relationship Diagram

Below is the Entity Relationship Diagram (ERD) for the FlexCart Backend:

![Entity Relationship Diagram](image/ER%20Diagram.png)

---

## Features

- **Flexible Campaign Structure**: Campaigns are built using a combination of `Condition` and `Action` classes, making it easy to define complex promotional rules.
- **Dynamic Conditions**: Conditions determine whether a campaign is applicable to a given cart. Examples include:
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
    boolean isSatisfiedBy(Cart cart);
}
```

### 2. **Actions**

Actions define the benefits or discounts provided by the campaign. Each action implements the `Action` interface:

```java
public interface Action {
    BigDecimal apply(Cart cart);
}
```

#### Examples:

- **PercentageOnTotalAction**: Applies a percentage discount on the total cart value.
- **FixedAmountAction**: Deducts a fixed amount from the total cart value.
- **FreeShippingAction**: Provides free shipping for the cart.
- **FreeUnitsOnCategoryAction**: Adds free units of a product or category to the cart.

---

## Campaign Creation Workflow

1. **Define Conditions**: Specify the rules under which the campaign will be applicable.
2. **Define Actions**: Specify the benefits or discounts to be applied when the campaign is triggered.
3. **Combine Conditions and Actions**: Use the `PostCampaignRequest` schema to define a campaign with a list of conditions and actions.

### Example JSON for Campaign Creation

```json
{
  "name": "Summer Sale",
  "description": "Discounts on summer products",
  "startDate": "2023-06-01T00:00:00",
  "endDate": "2023-06-30T23:59:59",
  "actions": [
    {
      "type": "PERCENTAGE_ON_TOTAL",
      "parameters": {
        "percent": 10
      }
    },
    {
      "type": "FREE_SHIPPING",
      "parameters": {}
    }
  ],
  "conditions": [
    {
      "type": "MIN_TOTAL",
      "parameters": {
        "amount": 100
      }
    },
    {
      "type": "DAY_OF_WEEK",
      "parameters": {
        "dayOfWeek": "MONDAY"
      }
    }
  ],
  "type": "PRICE"
}
```

#### Note: "type" parameter is used to set if Campaign is a PRICE or SHIPPING campaign. If shipping, the campaign added after the best possible PRICE campaign is applied.

---

## Extensibility

The system is designed to be easily extensible:

- **Add New Conditions**: Implement the `Condition` interface and register the new condition in the `ConditionFactory`.
- **Add New Actions**: Implement the `Action` interface and register the new action in the `ActionFactory`.

---

## Example Use Case

### Scenario: Free Shipping on Mondays for Orders Above $100

- **Condition**:
  - `DayOfWeekCondition` with `dayOfWeek = MONDAY`.
  - `MinTotalCondition` with `amount = 100`.
- **Action**:
  - `FreeShippingAction`.

This campaign can be defined using the `PostCampaignRequest` schema and will automatically apply free shipping to eligible carts.

---

## Conclusion

The flexible campaign management system in **FlexCart Backend** empowers businesses to define and manage promotional campaigns with ease. By leveraging the modular `Condition` and `Action` classes, the system ensures scalability and adaptability to meet diverse business needs.
