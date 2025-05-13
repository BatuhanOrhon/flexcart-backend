package com.app.flexcart.flexcart.backend.controller.schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.app.flexcart.flexcart.backend.domain.campaign.CampaignType;
import com.app.flexcart.flexcart.backend.domain.campaign.action.ActionType;
import com.app.flexcart.flexcart.backend.domain.campaign.condition.ConditionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PostCampaignRequest {
    @NotNull
    private String name;
    private String description;
    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime startDate;
    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime endDate;
    @NotNull
    @NotEmpty
    private List<ActionRequest> actions;
    @NotNull
    @NotEmpty
    private List<ConditionRequest> conditions;
    @NotNull
    private CampaignType type;

    @Getter
    @Setter
    public static class ActionRequest {
        private ActionType type;
        private Map<String, Object> parameters;

        public ActionRequest(ActionType type, Map<String, Object> parameters) {
            this.type = type;
            this.parameters = parameters;
        }
    }

    @Getter
    @Setter
    public static class ConditionRequest {
        @NotNull
        private ConditionType type;
        @NotNull
        private Map<String, Object> parameters;

        public ConditionRequest(ConditionType type, Map<String, Object> parameters) {
            this.type = type;
            this.parameters = parameters;
        }
    }
}
