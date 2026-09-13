package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeOptionResponse {

    private Long id;
    private Long nodeId;
    private String optionLabel;
    private Long nextNodeId;
}