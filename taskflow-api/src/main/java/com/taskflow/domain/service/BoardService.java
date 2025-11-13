package com.taskflow.domain.service;

import com.taskflow.domain.model.Board;
import com.taskflow.domain.model.User;
import com.taskflow.domain.port.in.CreateBoardUseCase;
import com.taskflow.domain.port.in.GetBoardsByOwnerUseCase;
import com.taskflow.domain.port.out.BoardRepositoryPort;
import com.taskflow.domain.port.out.UserRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService implements CreateBoardUseCase, GetBoardsByOwnerUseCase {
  
  private final BoardRepositoryPort boardRepositoryPort;
  private final UserRepositoryPort userRepositoryPort;

  @Override
  public Board createBoard(CreateBoardCommand command, String ownerUsername) {
    // Find the owner of the board
    User owner = userRepositoryPort.findByEmail(ownerUsername)
                                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));
    // Create the Board object
    Board newBoard = Board.builder()
                          .title(command.getTitle())
                          .userId(owner.getId())
                          .build();
    // Save it using the persistence port
    return boardRepositoryPort.save(newBoard);
  }

  @Override
  public List<Board> getBoards(String ownerUsername) {
    // Find the user 
    User owner = userRepositoryPort.findByEmail(ownerUsername)
                                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));
    // Ask for all of tasklists for that userId
    return boardRepositoryPort.findAllByUserId(owner.getId());
  }
}
