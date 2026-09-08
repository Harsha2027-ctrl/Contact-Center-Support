package com.contactcenter.contactcenterkb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TreeNodeRequest {

    @NotNull
    private Long treeId;

    @NotBlank
    private String nodeType;

    private String questionText;

    private String resolutionText;

    private Long parentNodeId;
}