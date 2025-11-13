package com.taskflow.domain.port.out;

import com.taskflow.domain.model.Board;
import java.util.List;

public interface BoardRepositoryPort {
  
  // save board
  Board save(Board board);

  // Find all tasklists
  List<Board> findAllByUserId(Long userId);

  // TODO: rest of the crud
}
