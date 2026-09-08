package com.contactcenter.contactcenterkb.controller;

import com.contactcenter.contactcenterkb.dto.TreeRequest;
import com.contactcenter.contactcenterkb.dto.TreeResponse;
import com.contactcenter.contactcenterkb.entity.Tree;
import com.contactcenter.contactcenterkb.entity.User;
import com.contactcenter.contactcenterkb.repository.UserRepository;
import com.contactcenter.contactcenterkb.service.TreeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TreeResponse> createTree(@Valid @RequestBody TreeRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Tree tree = treeService.createTree(request.getCategoryId(), request.getName(), currentUser);
        return ResponseEntity.ok(treeService.toResponse(tree));
    }

    @GetMapping
    public ResponseEntity<List<TreeResponse>> getAllTrees() {
        List<TreeResponse> trees = treeService.getAllTrees().stream()
                .map(treeService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trees);
    }

    @GetMapping("/published")
    public ResponseEntity<List<TreeResponse>> getPublishedTrees() {
        List<TreeResponse> trees = treeService.getPublishedTrees().stream()
                .map(treeService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trees);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TreeResponse>> getTreesByCategory(@PathVariable Long categoryId) {
        List<TreeResponse> trees = treeService.getTreesByCategory(categoryId).stream()
                .map(treeService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeResponse> getTreeById(@PathVariable Long id) {
        return ResponseEntity.ok(treeService.toResponse(treeService.getTreeById(id)));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<TreeResponse> publishTree(@PathVariable Long id) {
        return ResponseEntity.ok(treeService.toResponse(treeService.publishTree(id)));
    }

    @PutMapping("/{id}/unpublish")
    public ResponseEntity<TreeResponse> unpublishTree(@PathVariable Long id) {
        return ResponseEntity.ok(treeService.toResponse(treeService.unpublishTree(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {
        treeService.deleteTree(id);
        return ResponseEntity.noContent().build();
    }
}