package com.contactcenter.contactcenterkb.service;

import com.contactcenter.contactcenterkb.dto.TreeResponse;
import com.contactcenter.contactcenterkb.entity.Category;
import com.contactcenter.contactcenterkb.entity.NodeOption;
import com.contactcenter.contactcenterkb.entity.Session;
import com.contactcenter.contactcenterkb.entity.SessionPath;
import com.contactcenter.contactcenterkb.entity.Tree;
import com.contactcenter.contactcenterkb.entity.TreeNode;
import com.contactcenter.contactcenterkb.entity.User;
import com.contactcenter.contactcenterkb.repository.CategoryRepository;
import com.contactcenter.contactcenterkb.repository.NodeOptionRepository;
import com.contactcenter.contactcenterkb.repository.SessionPathRepository;
import com.contactcenter.contactcenterkb.repository.SessionRepository;
import com.contactcenter.contactcenterkb.repository.TreeNodeRepository;
import com.contactcenter.contactcenterkb.repository.TreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final TreeRepository treeRepository;
    private final CategoryRepository categoryRepository;
    private final SessionRepository sessionRepository;
    private final SessionPathRepository sessionPathRepository;
    private final TreeNodeRepository treeNodeRepository;
    private final NodeOptionRepository nodeOptionRepository;

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

    @Transactional
    public void deleteTree(Long id) {
        Tree tree = getTreeById(id);

        // 1. Delete SessionPaths of sessions belonging to this tree
        List<Session> sessions = sessionRepository.findByTreeId(id);
        for (Session session : sessions) {
            List<SessionPath> paths = sessionPathRepository.findBySessionId(session.getId());
            sessionPathRepository.deleteAll(paths);
        }

        // 2. Delete Sessions of this tree
        sessionRepository.deleteAll(sessions);

        // 3. Delete NodeOptions of nodes belonging to this tree
        List<TreeNode> nodes = treeNodeRepository.findByTreeId(id);
        for (TreeNode node : nodes) {
            List<NodeOption> options = nodeOptionRepository.findByNodeId(node.getId());
            nodeOptionRepository.deleteAll(options);
        }

        // 4. Delete TreeNodes of this tree
        treeNodeRepository.deleteAll(nodes);

        // 5. Finally delete the Tree
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