package com.app.flexcart.flexcart.backend.controller.schema.subclasses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CampaignResponse {
    private long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
