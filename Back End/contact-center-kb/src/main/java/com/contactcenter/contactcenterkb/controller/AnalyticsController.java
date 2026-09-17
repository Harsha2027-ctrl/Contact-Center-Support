package com.contactcenter.contactcenterkb.controller;

import com.contactcenter.contactcenterkb.dto.TreeAnalyticsResponse;
import com.contactcenter.contactcenterkb.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    @GetMapping("/tree/{treeId}")
    public ResponseEntity<TreeAnalyticsResponse> getTreeAnalytics(@PathVariable Long treeId) {
        return ResponseEntity.ok(analyticsService.getTreeAnalytics(treeId));
    }
}