package com.contactcenter.contactcenterkb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "node_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "node_id", nullable = false)
    private TreeNode node;

    @NotBlank
    @Column(name = "option_label", nullable = false)
    private String optionLabel;

    @ManyToOne
    @JoinColumn(name = "next_node_id", nullable = false)
    private TreeNode nextNode;
}