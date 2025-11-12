package com.taskflow.infrastructure.adapter.out.persistence.repository;

import com.taskflow.infrastructure.adapter.out.persistence.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {
  
  // Spring JPA creates the SQL query
  List<BoardEntity> findAllByOwnerId(Long userId);
}
