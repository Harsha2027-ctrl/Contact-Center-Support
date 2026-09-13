package com.contactcenter.contactcenterkb.controller;

import com.contactcenter.contactcenterkb.dto.NodeOptionRequest;
import com.contactcenter.contactcenterkb.dto.NodeOptionResponse;
import com.contactcenter.contactcenterkb.entity.NodeOption;
import com.contactcenter.contactcenterkb.service.NodeOptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/node-options")
@RequiredArgsConstructor
public class NodeOptionController {

    private final NodeOptionService nodeOptionService;

    @PostMapping
    public ResponseEntity<NodeOptionResponse> createOption(@Valid @RequestBody NodeOptionRequest request) {
        NodeOption option = nodeOptionService.createOption(
                request.getNodeId(),
                request.getOptionLabel(),
                request.getNextNodeId()
        );
        return ResponseEntity.ok(nodeOptionService.toResponse(option));
    }

    @GetMapping("/node/{nodeId}")
    public ResponseEntity<List<NodeOptionResponse>> getOptionsByNode(@PathVariable Long nodeId) {
        List<NodeOptionResponse> options = nodeOptionService.getOptionsByNode(nodeId).stream()
                .map(nodeOptionService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(options);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NodeOptionResponse> getOptionById(@PathVariable Long id) {
        return ResponseEntity.ok(nodeOptionService.toResponse(nodeOptionService.getOptionById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id) {
        nodeOptionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}