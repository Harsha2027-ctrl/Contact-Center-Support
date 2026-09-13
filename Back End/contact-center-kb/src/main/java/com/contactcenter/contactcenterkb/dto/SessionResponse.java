package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {

    private Long id;
    private Long treeId;
    private String treeName;
    private Long agentId;
    private String agentName;
    private String ticketRef;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Boolean resolved;
    private Boolean escalated;
}