package com.taskflow.infrastructure.adapter.out.persistence.mapper;

import com.taskflow.domain.model.Board;
import com.taskflow.infrastructure.adapter.out.persistence.entity.BoardEntity;
import org.springframework.stereotype.Component;

@Component
public class BoardPersistenceMapper {

  // from entity to domain model
  public Board toDomain(BoardEntity entity) {
    if (entity == null) {
      return null;
    }
    return Board.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .userId(entity.getOwner() != null ? entity.getOwner().getId() : null)
                .build();
  }

  // from domain to Entity (BBDD)
  public BoardEntity toEntity(Board domain) {
    if(domain == null) {
      return null;
    }
    BoardEntity entity = new BoardEntity();
    entity.setId(domain.getId());
    entity.setTitle(domain.getTitle());
    // Owner asigned on adapter cuz mapper should not be doing DataBase logic
    return entity;
  }
  
}
