package com.contactcenter.contactcenterkb.service;

import com.contactcenter.contactcenterkb.entity.Category;
import com.contactcenter.contactcenterkb.entity.Tree;
import com.contactcenter.contactcenterkb.entity.User;
import com.contactcenter.contactcenterkb.repository.CategoryRepository;
import com.contactcenter.contactcenterkb.repository.TreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.contactcenter.contactcenterkb.dto.TreeResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final TreeRepository treeRepository;
    private final CategoryRepository categoryRepository;

    public Tree createTree(Long categoryId, String name, User createdBy) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

        Tree tree = new Tree();
        tree.setCategory(category);
        tree.setName(name);
        tree.setCreatedBy(createdBy);
        tree.setVersion(1);
        tree.setIsPublished(false);

        return treeRepository.save(tree);
    }

    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    public List<Tree> getTreesByCategory(Long categoryId) {
        return treeRepository.findByCategoryId(categoryId);
    }

    public List<Tree> getPublishedTrees() {
        return treeRepository.findByIsPublishedTrue();
    }

    public Tree getTreeById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with id: " + id));
    }

    public Tree publishTree(Long id) {
        Tree tree = getTreeById(id);
        tree.setIsPublished(true);
        return treeRepository.save(tree);
    }

    public Tree unpublishTree(Long id) {
        Tree tree = getTreeById(id);
        tree.setIsPublished(false);
        return treeRepository.save(tree);
    }

    public void deleteTree(Long id) {
        Tree tree = getTreeById(id);
        treeRepository.delete(tree);
    }

    public TreeResponse toResponse(Tree tree) {
        return new TreeResponse(
                tree.getId(),
                tree.getCategory().getId(),
                tree.getCategory().getName(),
                tree.getName(),
                tree.getVersion(),
                tree.getIsPublished(),
                tree.getCreatedBy() != null ? tree.getCreatedBy().getName() : null,
                tree.getCreatedAt()
        );
    }
}