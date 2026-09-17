package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeAnalyticsResponse {

    private Long treeId;
    private String treeName;
    private Long totalSessions;
    private Long resolvedCount;
    private Long escalatedCount;
    private Long inProgressCount;
    private Double escalationRatePercent;
    private Double avgResolutionTimeSeconds;
    private List<NodeVisitCount> mostVisitedNodes;
}