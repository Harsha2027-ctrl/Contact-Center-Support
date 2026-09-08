package com.contactcenter.contactcenterkb.controller;

import com.contactcenter.contactcenterkb.dto.TreeNodeRequest;
import com.contactcenter.contactcenterkb.dto.TreeNodeResponse;
import com.contactcenter.contactcenterkb.entity.TreeNode;
import com.contactcenter.contactcenterkb.service.TreeNodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tree-nodes")
@RequiredArgsConstructor
public class TreeNodeController {

    private final TreeNodeService treeNodeService;

    @PostMapping
    public ResponseEntity<TreeNodeResponse> createNode(@Valid @RequestBody TreeNodeRequest request) {
        TreeNode node = treeNodeService.createNode(
                request.getTreeId(),
                request.getNodeType(),
                request.getQuestionText(),
                request.getResolutionText(),
                request.getParentNodeId()
        );
        return ResponseEntity.ok(treeNodeService.toResponse(node));
    }

    @GetMapping("/tree/{treeId}")
    public ResponseEntity<List<TreeNodeResponse>> getNodesByTree(@PathVariable Long treeId) {
        List<TreeNodeResponse> nodes = treeNodeService.getNodesByTree(treeId).stream()
                .map(treeNodeService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(nodes);
    }

    @GetMapping("/tree/{treeId}/root")
    public ResponseEntity<TreeNodeResponse> getRootNode(@PathVariable Long treeId) {
        return ResponseEntity.ok(treeNodeService.toResponse(treeNodeService.getRootNode(treeId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeNodeResponse> getNodeById(@PathVariable Long id) {
        return ResponseEntity.ok(treeNodeService.toResponse(treeNodeService.getNodeById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        treeNodeService.deleteNode(id);
        return ResponseEntity.noContent().build();
    }
}