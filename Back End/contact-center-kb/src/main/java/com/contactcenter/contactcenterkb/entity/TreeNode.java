package com.contactcenter.contactcenterkb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tree_nodes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;

    @Enumerated(EnumType.STRING)
    @Column(name = "node_type", nullable = false)
    private NodeType nodeType;

    @Column(name = "question_text", length = 1000)
    private String questionText;

    @Column(name = "resolution_text", length = 2000)
    private String resolutionText;

    @ManyToOne
    @JoinColumn(name = "parent_node_id")
    private TreeNode parentNode;

    public enum NodeType {
        QUESTION, LEAF
    }
}