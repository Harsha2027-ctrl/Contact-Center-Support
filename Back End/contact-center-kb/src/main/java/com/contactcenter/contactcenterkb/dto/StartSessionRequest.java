package com.contactcenter.contactcenterkb.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StartSessionRequest {

    @NotNull
    private Long treeId;

    private String ticketRef;
}