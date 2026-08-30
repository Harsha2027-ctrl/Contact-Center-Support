package com.contactcenter.contactcenterkb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TreeRequest {

    @NotNull
    private Long categoryId;

    @NotBlank
    private String name;
}