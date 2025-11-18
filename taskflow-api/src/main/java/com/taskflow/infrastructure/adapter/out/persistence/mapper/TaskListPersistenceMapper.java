package com.taskflow.infrastructure.adapter.out.persistence.mapper;

import com.taskflow.domain.model.TaskList;
import com.taskflow.infrastructure.adapter.out.persistence.entity.TaskListEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskListPersistenceMapper {
  
  public TaskList toDomain(TaskListEntity entity) {
    if (entity == null) return null;
    return TaskList.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .boardId(entity.getBoard() != null ? entity.getBoard().getId() : null)
            .build();
  }

  public TaskListEntity toEntity(TaskList domain) {
    if (domain == null) return null;
    TaskListEntity entity = new TaskListEntity();
    entity.setId(domain.getId());
    entity.setTitle(domain.getTitle());
    // boardId assigned on the adapter
    return entity;
  }
}
