package com.taskflow.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@Builder // Implements the builder pattern
@NoArgsConstructor
@AllArgsConstructor
public class TaskList {
  private Long id;
  private String title;
  // reference to the parent board
  private Long boardId;
}
