package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodeResponse {

    private Long id;
    private Long treeId;
    private String nodeType;
    private String questionText;
    private String resolutionText;
    private Long parentNodeId;
}