package com.contactcenter.contactcenterkb.repository;

import com.contactcenter.contactcenterkb.entity.SessionPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionPathRepository extends JpaRepository<SessionPath, Long> {

    List<SessionPath> findBySessionId(Long sessionId);

    List<SessionPath> findByNodeId(Long nodeId);
}