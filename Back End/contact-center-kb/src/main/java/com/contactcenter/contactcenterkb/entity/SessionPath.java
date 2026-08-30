package com.contactcenter.contactcenterkb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "session_path")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "node_id", nullable = false)
    private TreeNode node;

    @Column(name = "option_selected")
    private String optionSelected;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}