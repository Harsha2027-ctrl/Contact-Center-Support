package com.contactcenter.contactcenterkb.controller;

import com.contactcenter.contactcenterkb.dto.SessionResponse;
import com.contactcenter.contactcenterkb.dto.StartSessionRequest;
import com.contactcenter.contactcenterkb.entity.Session;
import com.contactcenter.contactcenterkb.entity.User;
import com.contactcenter.contactcenterkb.repository.UserRepository;
import com.contactcenter.contactcenterkb.service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final UserRepository userRepository;

    @PostMapping("/start")
    public ResponseEntity<SessionResponse> startSession(@Valid @RequestBody StartSessionRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentAgent = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Session session = sessionService.startSession(request.getTreeId(), currentAgent, request.getTicketRef());
        return ResponseEntity.ok(sessionService.toResponse(session));
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> getAllSessions() {
        List<SessionResponse> sessions = sessionService.getAllSessions().stream()
                .map(sessionService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.toResponse(sessionService.getSessionById(id)));
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<SessionResponse>> getSessionsByAgent(@PathVariable Long agentId) {
        List<SessionResponse> sessions = sessionService.getSessionsByAgent(agentId).stream()
                .map(sessionService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/tree/{treeId}")
    public ResponseEntity<List<SessionResponse>> getSessionsByTree(@PathVariable Long treeId) {
        List<SessionResponse> sessions = sessionService.getSessionsByTree(treeId).stream()
                .map(sessionService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessions);
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<SessionResponse> resolveSession(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.toResponse(sessionService.resolveSession(id)));
    }

    @PutMapping("/{id}/escalate")
    public ResponseEntity<SessionResponse> escalateSession(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.toResponse(sessionService.escalateSession(id)));
    }
}