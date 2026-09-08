package com.contactcenter.contactcenterkb.service;

import com.contactcenter.contactcenterkb.entity.NodeOption;
import com.contactcenter.contactcenterkb.entity.TreeNode;
import com.contactcenter.contactcenterkb.repository.NodeOptionRepository;
import com.contactcenter.contactcenterkb.repository.TreeNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NodeOptionService {

    private final NodeOptionRepository nodeOptionRepository;
    private final TreeNodeRepository treeNodeRepository;

    public NodeOption createOption(Long nodeId, String optionLabel, Long nextNodeId) {
        TreeNode node = treeNodeRepository.findById(nodeId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found with id: " + nodeId));

        TreeNode nextNode = treeNodeRepository.findById(nextNodeId)
                .orElseThrow(() -> new IllegalArgumentException("Next node not found with id: " + nextNodeId));

        NodeOption option = new NodeOption();
        option.setNode(node);
        option.setOptionLabel(optionLabel);
        option.setNextNode(nextNode);

        return nodeOptionRepository.save(option);
    }

    public List<NodeOption> getOptionsByNode(Long nodeId) {
        return nodeOptionRepository.findByNodeId(nodeId);
    }

    public NodeOption getOptionById(Long id) {
        return nodeOptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Option not found with id: " + id));
    }

    public void deleteOption(Long id) {
        NodeOption option = getOptionById(id);
        nodeOptionRepository.delete(option);
    }
}