package com.contactcenter.contactcenterkb.repository;

import com.contactcenter.contactcenterkb.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByAgentId(Long agentId);

    List<Session> findByTreeId(Long treeId);

    List<Session> findByResolvedTrue();

    List<Session> findByEscalatedTrue();
}