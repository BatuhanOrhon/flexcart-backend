package com.app.flexcart.flexcart.backend.controller.schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.app.flexcart.flexcart.backend.domain.campaign.CampaignType;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Schema(description = "Request object for creating a new campaign")
public class PostCampaignRequest {

    @NotNull
    @Schema(description = "Name of the campaign", example = "Summer Sale", required = true)
    private String name;

    @Schema(description = "Description of the campaign", example = "Discounts on summer products")
    private String description;

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    @Schema(description = "Start date of the campaign. Must be in the future", example = "01.06.2023 00:00:00", required = true)
    private LocalDateTime startDate;

    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    @Schema(description = "End date of the campaign. . Must be in the future", example = "30.06.2023 23:59:59", required = true)
    private LocalDateTime endDate;

    @NotNull
    @NotEmpty
    @ArraySchema(schema = @Schema(implementation = ActionRequest.class), arraySchema = @Schema(description = "List of actions to be applied in the campaign", required = true))
    private List<ActionRequest> actions;

    @NotNull
    @NotEmpty
    @ArraySchema(schema = @Schema(implementation = ConditionRequest.class), arraySchema = @Schema(description = "List of conditions for the campaign", required = true))
    private List<ConditionRequest> conditions;

    @NotNull
    @Schema(description = "Type of the campaign", example = "PRICE", required = true)
    private CampaignType type;

    @Getter
    @Setter
    @Schema(description = "Action to be applied in the campaign")
    public static class ActionRequest {

        @NotNull
        @Schema(description = "Type of the action", example = "DISCOUNT", required = true)
        private ActionType type;

        @Schema(description = "Parameters for the action", example = "{\"percentage\": 10, \"maxAmount\": 100}")
        private Map<String, Object> parameters;

        public ActionRequest(ActionType type, Map<String, Object> parameters) {
            this.type = type;
            this.parameters = parameters;
        }
    }

    @Getter
    @Setter
    @Schema(description = "Condition for the campaign")
    public static class ConditionRequest {

        @NotNull
        @Schema(description = "Type of the condition", example = "MIN_TOTAL", required = true)
        private ConditionType type;

        @NotNull
        @Schema(description = "Parameters for the condition", example = "{\"amount\": 100}")
        private Map<String, Object> parameters;

        public ConditionRequest(ConditionType type, Map<String, Object> parameters) {
            this.type = type;
            this.parameters = parameters;
        }
    }
}
