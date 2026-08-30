package com.contactcenter.contactcenterkb.repository;

import com.contactcenter.contactcenterkb.entity.NodeOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeOptionRepository extends JpaRepository<NodeOption, Long> {

    List<NodeOption> findByNodeId(Long nodeId);
}