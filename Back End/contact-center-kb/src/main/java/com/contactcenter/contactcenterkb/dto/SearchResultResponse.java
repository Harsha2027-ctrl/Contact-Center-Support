package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultResponse {

    private Long nodeId;
    private Long treeId;
    private String treeName;
    private String nodeType;
    private String matchedText;
    private Double relevanceScore;
}