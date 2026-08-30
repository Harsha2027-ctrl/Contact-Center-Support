package com.contactcenter.contactcenterkb.controller;

import com.contactcenter.contactcenterkb.dto.TreeRequest;
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

@RestController
@RequestMapping("/api/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Tree> createTree(@Valid @RequestBody TreeRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Tree tree = treeService.createTree(request.getCategoryId(), request.getName(), currentUser);
        return ResponseEntity.ok(tree);
    }

    @GetMapping
    public ResponseEntity<List<Tree>> getAllTrees() {
        return ResponseEntity.ok(treeService.getAllTrees());
    }

    @GetMapping("/published")
    public ResponseEntity<List<Tree>> getPublishedTrees() {
        return ResponseEntity.ok(treeService.getPublishedTrees());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Tree>> getTreesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(treeService.getTreesByCategory(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tree> getTreeById(@PathVariable Long id) {
        return ResponseEntity.ok(treeService.getTreeById(id));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<Tree> publishTree(@PathVariable Long id) {
        return ResponseEntity.ok(treeService.publishTree(id));
    }

    @PutMapping("/{id}/unpublish")
    public ResponseEntity<Tree> unpublishTree(@PathVariable Long id) {
        return ResponseEntity.ok(treeService.unpublishTree(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {
        treeService.deleteTree(id);
        return ResponseEntity.noContent().build();
    }
}