package com.contactcenter.contactcenterkb.service;

import com.contactcenter.contactcenterkb.dto.SearchResultResponse;
import com.contactcenter.contactcenterkb.entity.TreeNode;
import com.contactcenter.contactcenterkb.repository.TreeNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TreeNodeRepository treeNodeRepository;

    private static final Set<String> STOP_WORDS = Set.of(
            "the", "a", "an", "is", "are", "was", "were", "i", "my", "me",
            "to", "for", "of", "on", "in", "and", "or", "with", "want", "need"
    );

    @Transactional(readOnly = true)
    public List<SearchResultResponse> search(String query) {
        Set<String> queryWords = tokenize(query);

        List<TreeNode> allNodes = treeNodeRepository.findAll();

        return allNodes.stream()
                .map(node -> {
                    String text = node.getNodeType() == TreeNode.NodeType.QUESTION
                            ? node.getQuestionText()
                            : node.getResolutionText();

                    if (text == null) text = "";

                    Set<String> nodeWords = tokenize(text);
                    double score = calculateOverlapScore(queryWords, nodeWords);

                    String treeName = "Unknown";
                    Long treeId = null;
                    try {
                        if (node.getTree() != null) {
                            treeName = node.getTree().getName();
                            treeId = node.getTree().getId();
                        }
                    } catch (Exception e) {
                        // ignore lazy loading issues
                    }

                    return new SearchResultResponse(
                            node.getId(),
                            treeId,
                            treeName,
                            node.getNodeType().name(),
                            text,
                            score
                    );
                })
                .filter(result -> result.getRelevanceScore() > 0)
                .sorted((a, b) -> Double.compare(b.getRelevanceScore(), a.getRelevanceScore()))
                .limit(10)
                .collect(Collectors.toList());
    }

    private Set<String> tokenize(String text) {
        if (text == null || text.isBlank()) {
            return new HashSet<>();
        }
        return Arrays.stream(text.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+"))
                .filter(word -> !word.isBlank() && !STOP_WORDS.contains(word))
                .collect(Collectors.toSet());
    }

    private double calculateOverlapScore(Set<String> queryWords, Set<String> nodeWords) {
        if (queryWords.isEmpty() || nodeWords.isEmpty()) {
            return 0.0;
        }
        long matchCount = queryWords.stream().filter(nodeWords::contains).count();
        return (double) matchCount / queryWords.size();
    }
}