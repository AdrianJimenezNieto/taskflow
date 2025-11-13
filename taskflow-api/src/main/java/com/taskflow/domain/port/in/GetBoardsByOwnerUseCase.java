package com.taskflow.domain.port.in;

import com.taskflow.domain.model.Board;
import java.util.List;

public interface GetBoardsByOwnerUseCase {
  List<Board> getBoards(String ownerUsername);
}
