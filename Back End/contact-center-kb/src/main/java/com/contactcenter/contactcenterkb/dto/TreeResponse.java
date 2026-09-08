package com.contactcenter.contactcenterkb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeResponse {

    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private Integer version;
    private Boolean isPublished;
    private String createdByName;
    private LocalDateTime createdAt;
}