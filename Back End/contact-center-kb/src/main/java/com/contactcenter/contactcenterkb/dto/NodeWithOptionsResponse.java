package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeWithOptionsResponse {

    private Long nodeId;
    private String nodeType;
    private String questionText;
    private String resolutionText;
    private List<NodeOptionResponse> options;
}