package com.contactcenter.contactcenterkb.controller;

import com.contactcenter.contactcenterkb.dto.LogStepRequest;
import com.contactcenter.contactcenterkb.dto.SessionPathResponse;
import com.contactcenter.contactcenterkb.entity.SessionPath;
import com.contactcenter.contactcenterkb.service.SessionPathService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/session-paths")
@RequiredArgsConstructor
public class SessionPathController {

    private final SessionPathService sessionPathService;

    @PostMapping("/log")
    public ResponseEntity<SessionPathResponse> logStep(@Valid @RequestBody LogStepRequest request) {
        SessionPath path = sessionPathService.logStep(
                request.getSessionId(),
                request.getNodeId(),
                request.getOptionSelected()
        );
        return ResponseEntity.ok(sessionPathService.toResponse(path));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<SessionPathResponse>> getPathBySession(@PathVariable Long sessionId) {
        List<SessionPathResponse> paths = sessionPathService.getPathBySession(sessionId).stream()
                .map(sessionPathService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paths);
    }

    @GetMapping("/node/{nodeId}")
    public ResponseEntity<List<SessionPathResponse>> getPathByNode(@PathVariable Long nodeId) {
        List<SessionPathResponse> paths = sessionPathService.getPathByNode(nodeId).stream()
                .map(sessionPathService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paths);
    }
}