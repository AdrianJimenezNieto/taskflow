package com.taskflow.infrastructure.adapter.out.persistence.repository;

import com.taskflow.infrastructure.adapter.out.persistence.entity.TaskListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskListJpaRepository extends JpaRepository<TaskListEntity, Long> {

  List<TaskListEntity> findAllByBoardId(Long boardId);
  
}
