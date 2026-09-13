package com.contactcenter.contactcenterkb.service;

import com.contactcenter.contactcenterkb.dto.SessionPathResponse;
import com.contactcenter.contactcenterkb.entity.Session;
import com.contactcenter.contactcenterkb.entity.SessionPath;
import com.contactcenter.contactcenterkb.entity.TreeNode;
import com.contactcenter.contactcenterkb.repository.SessionPathRepository;
import com.contactcenter.contactcenterkb.repository.SessionRepository;
import com.contactcenter.contactcenterkb.repository.TreeNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionPathService {

    private final SessionPathRepository sessionPathRepository;
    private final SessionRepository sessionRepository;
    private final TreeNodeRepository treeNodeRepository;

    public SessionPath logStep(Long sessionId, Long nodeId, String optionSelected) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found with id: " + sessionId));

        TreeNode node = treeNodeRepository.findById(nodeId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found with id: " + nodeId));

        SessionPath path = new SessionPath();
        path.setSession(session);
        path.setNode(node);
        path.setOptionSelected(optionSelected);
        path.setTimestamp(LocalDateTime.now());

        return sessionPathRepository.save(path);
    }

    public List<SessionPath> getPathBySession(Long sessionId) {
        return sessionPathRepository.findBySessionId(sessionId);
    }

    public List<SessionPath> getPathByNode(Long nodeId) {
        return sessionPathRepository.findByNodeId(nodeId);
    }

    public SessionPathResponse toResponse(SessionPath path) {
        return new SessionPathResponse(
                path.getId(),
                path.getSession().getId(),
                path.getNode().getId(),
                path.getOptionSelected(),
                path.getTimestamp()
        );
    }
}