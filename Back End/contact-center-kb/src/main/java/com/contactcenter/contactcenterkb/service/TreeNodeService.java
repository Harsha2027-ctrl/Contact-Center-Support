package com.contactcenter.contactcenterkb.service;

import com.contactcenter.contactcenterkb.dto.NodeOptionResponse;
import com.contactcenter.contactcenterkb.dto.NodeWithOptionsResponse;
import com.contactcenter.contactcenterkb.dto.TreeNodeResponse;
import com.contactcenter.contactcenterkb.entity.Tree;
import com.contactcenter.contactcenterkb.entity.TreeNode;
import com.contactcenter.contactcenterkb.repository.TreeNodeRepository;
import com.contactcenter.contactcenterkb.repository.TreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreeNodeService {

    private final TreeNodeRepository treeNodeRepository;
    private final TreeRepository treeRepository;
    private final NodeOptionService nodeOptionService;

    public TreeNode createNode(Long treeId, String nodeType, String questionText,
                               String resolutionText, Long parentNodeId) {
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with id: " + treeId));

        TreeNode node = new TreeNode();
        node.setTree(tree);
        node.setNodeType(TreeNode.NodeType.valueOf(nodeType.toUpperCase()));
        node.setQuestionText(questionText);
        node.setResolutionText(resolutionText);

        if (parentNodeId != null) {
            TreeNode parent = treeNodeRepository.findById(parentNodeId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent node not found with id: " + parentNodeId));
            node.setParentNode(parent);
        }

        return treeNodeRepository.save(node);
    }

    public List<TreeNode> getNodesByTree(Long treeId) {
        return treeNodeRepository.findByTreeId(treeId);
    }

    public TreeNode getNodeById(Long id) {
        return treeNodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Node not found with id: " + id));
    }

    public TreeNode getRootNode(Long treeId) {
        return treeNodeRepository.findByTreeIdAndParentNodeIsNull(treeId)
                .orElseThrow(() -> new IllegalArgumentException("No root node found for tree id: " + treeId));
    }

    public void deleteNode(Long id) {
        TreeNode node = getNodeById(id);
        treeNodeRepository.delete(node);
    }

    public TreeNodeResponse toResponse(TreeNode node) {
        return new TreeNodeResponse(
                node.getId(),
                node.getTree().getId(),
                node.getNodeType().name(),
                node.getQuestionText(),
                node.getResolutionText(),
                node.getParentNode() != null ? node.getParentNode().getId() : null
        );
    }

    public NodeWithOptionsResponse getNodeWithOptions(Long nodeId) {
        TreeNode node = getNodeById(nodeId);

        List<NodeOptionResponse> options = nodeOptionService.getOptionsByNode(nodeId).stream()
                .map(nodeOptionService::toResponse)
                .collect(Collectors.toList());

        return new NodeWithOptionsResponse(
                node.getId(),
                node.getNodeType().name(),
                node.getQuestionText(),
                node.getResolutionText(),
                options
        );
    }
}