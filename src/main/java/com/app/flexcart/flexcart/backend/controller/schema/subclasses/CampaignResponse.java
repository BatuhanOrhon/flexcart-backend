package com.app.flexcart.flexcart.backend.controller.schema.subclasses;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Response object for campaign details")
public class CampaignResponse {

    @Schema(description = "ID of the campaign", example = "1", required = true)
    private long id;

    @Schema(description = "Name of the campaign", example = "Summer Sale", required = true)
    private String name;

    @Schema(description = "Description of the campaign", example = "Discounts on summer products")
    private String description;

    @Schema(description = "Start date of the campaign", example = "2023-06-01T00:00:00", required = true)
    private LocalDateTime startDate;

    @Schema(description = "End date of the campaign", example = "2023-06-30T23:59:59", required = true)
    private LocalDateTime endDate;
}
