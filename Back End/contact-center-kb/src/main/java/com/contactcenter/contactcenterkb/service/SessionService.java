package com.contactcenter.contactcenterkb.service;

import com.contactcenter.contactcenterkb.dto.SessionResponse;
import com.contactcenter.contactcenterkb.entity.Session;
import com.contactcenter.contactcenterkb.entity.Tree;
import com.contactcenter.contactcenterkb.entity.User;
import com.contactcenter.contactcenterkb.repository.SessionRepository;
import com.contactcenter.contactcenterkb.repository.TreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TreeRepository treeRepository;

    public Session startSession(Long treeId, User agent, String ticketRef) {
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with id: " + treeId));

        Session session = new Session();
        session.setTree(tree);
        session.setAgent(agent);
        session.setTicketRef(ticketRef);
        session.setStartedAt(LocalDateTime.now());
        session.setResolved(false);
        session.setEscalated(false);

        return sessionRepository.save(session);
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found with id: " + id));
    }

    public List<Session> getSessionsByAgent(Long agentId) {
        return sessionRepository.findByAgentId(agentId);
    }

    public List<Session> getSessionsByTree(Long treeId) {
        return sessionRepository.findByTreeId(treeId);
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session resolveSession(Long id) {
        Session session = getSessionById(id);
        session.setResolved(true);
        session.setEndedAt(LocalDateTime.now());
        return sessionRepository.save(session);
    }

    public Session escalateSession(Long id) {
        Session session = getSessionById(id);
        session.setEscalated(true);
        session.setEndedAt(LocalDateTime.now());
        return sessionRepository.save(session);
    }

    public SessionResponse toResponse(Session session) {
        return new SessionResponse(
                session.getId(),
                session.getTree().getId(),
                session.getTree().getName(),
                session.getAgent().getId(),
                session.getAgent().getName(),
                session.getTicketRef(),
                session.getStartedAt(),
                session.getEndedAt(),
                session.getResolved(),
                session.getEscalated()
        );
    }
}