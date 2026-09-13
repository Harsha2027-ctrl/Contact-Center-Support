package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionPathResponse {

    private Long id;
    private Long sessionId;
    private Long nodeId;
    private String optionSelected;
    private LocalDateTime timestamp;
}