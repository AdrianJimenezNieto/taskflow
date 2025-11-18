package com.taskflow.infrastructure.adapter.out.persistence.repository;

import com.taskflow.infrastructure.adapter.out.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CardJpaRepository extends JpaRepository<CardEntity, Long> {
  
  // Spring Data build the query: "findAll" with the field "taskList" and "id"
  List<CardEntity> findAllByTaskListId(Long taskListId);
}
