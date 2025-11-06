package com.taskflow.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class CardEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  // Relationships
  // Owned by a TaskList
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_list_id", nullable = false)
  private TaskListEntity taskListEntity;

}
