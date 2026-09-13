package com.contactcenter.contactcenterkb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NodeOptionRequest {

    @NotNull
    private Long nodeId;

    @NotBlank
    private String optionLabel;

    @NotNull
    private Long nextNodeId;
}