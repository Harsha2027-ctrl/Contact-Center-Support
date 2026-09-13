package com.contactcenter.contactcenterkb.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogStepRequest {

    @NotNull
    private Long sessionId;

    @NotNull
    private Long nodeId;

    private String optionSelected;
}