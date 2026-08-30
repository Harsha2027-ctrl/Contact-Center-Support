package com.contactcenter.contactcenterkb.repository;

import com.contactcenter.contactcenterkb.entity.TreeNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TreeNodeRepository extends JpaRepository<TreeNode, Long> {

    List<TreeNode> findByTreeId(Long treeId);

    Optional<TreeNode> findByTreeIdAndParentNodeIsNull(Long treeId);
}