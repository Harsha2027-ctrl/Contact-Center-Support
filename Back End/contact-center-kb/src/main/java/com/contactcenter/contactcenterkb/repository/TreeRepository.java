package com.contactcenter.contactcenterkb.repository;

import com.contactcenter.contactcenterkb.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreeRepository extends JpaRepository<Tree, Long> {

    List<Tree> findByCategoryId(Long categoryId);

    List<Tree> findByIsPublishedTrue();
}