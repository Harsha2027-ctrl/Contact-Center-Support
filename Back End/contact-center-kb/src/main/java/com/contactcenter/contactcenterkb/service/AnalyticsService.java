package com.contactcenter.contactcenterkb.service;

import com.contactcenter.contactcenterkb.dto.NodeVisitCount;
import com.contactcenter.contactcenterkb.dto.TreeAnalyticsResponse;
import com.contactcenter.contactcenterkb.entity.Session;
import com.contactcenter.contactcenterkb.entity.SessionPath;
import com.contactcenter.contactcenterkb.entity.Tree;
import com.contactcenter.contactcenterkb.entity.TreeNode;
import com.contactcenter.contactcenterkb.repository.SessionPathRepository;
import com.contactcenter.contactcenterkb.repository.SessionRepository;
import com.contactcenter.contactcenterkb.repository.TreeNodeRepository;
import com.contactcenter.contactcenterkb.repository.TreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final SessionRepository sessionRepository;
    private final SessionPathRepository sessionPathRepository;
    private final TreeRepository treeRepository;
    private final TreeNodeRepository treeNodeRepository;

    public TreeAnalyticsResponse getTreeAnalytics(Long treeId) {
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with id: " + treeId));

        List<Session> sessions = sessionRepository.findByTreeId(treeId);

        long total = sessions.size();
        long resolved = sessions.stream().filter(s -> Boolean.TRUE.equals(s.getResolved())).count();
        long escalated = sessions.stream().filter(s -> Boolean.TRUE.equals(s.getEscalated())).count();
        long inProgress = total - resolved - escalated;

        double avgResolutionSeconds = sessions.stream()
                .filter(s -> Boolean.TRUE.equals(s.getResolved()) && s.getEndedAt() != null)
                .mapToLong(s -> Duration.between(s.getStartedAt(), s.getEndedAt()).getSeconds())
                .average()
                .orElse(0.0);

        double escalationRatePercent = total == 0 ? 0.0 : (escalated * 100.0 / total);

        Map<Long, Long> nodeVisitCounts = new HashMap<>();
        for (Session session : sessions) {
            List<SessionPath> paths = sessionPathRepository.findBySessionId(session.getId());
            for (SessionPath path : paths) {
                nodeVisitCounts.merge(path.getNode().getId(), 1L, Long::sum);
            }
        }

        List<NodeVisitCount> mostVisitedNodes = nodeVisitCounts.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(5)
                .map(entry -> {
                    TreeNode node = treeNodeRepository.findById(entry.getKey()).orElse(null);
                    String label = "Unknown node";
                    if (node != null) {
                        label = node.getQuestionText() != null ? node.getQuestionText() : node.getResolutionText();
                    }
                    return new NodeVisitCount(entry.getKey(), label, entry.getValue());
                })
                .collect(Collectors.toList());

        return new TreeAnalyticsResponse(
                tree.getId(),
                tree.getName(),
                total,
                resolved,
                escalated,
                inProgress,
                escalationRatePercent,
                avgResolutionSeconds,
                mostVisitedNodes
        );
    }
}