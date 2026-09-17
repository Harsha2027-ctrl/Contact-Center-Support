package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeVisitCount {

    private Long nodeId;
    private String nodeLabel;
    private Long visitCount;
}