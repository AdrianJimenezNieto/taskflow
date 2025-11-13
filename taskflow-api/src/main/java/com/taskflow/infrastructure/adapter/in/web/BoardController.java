package com.taskflow.infrastructure.adapter.in.web;

import com.taskflow.domain.model.Board;
import com.taskflow.domain.port.in.CreateBoardUseCase;
import com.taskflow.domain.port.in.GetBoardsByOwnerUseCase;
import com.taskflow.infrastructure.adapter.in.web.dto.BoardResponse;
import com.taskflow.infrastructure.adapter.in.web.dto.CreateBoardRequest;
import com.taskflow.infrastructure.adapter.in.web.mapper.BoardWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {
  
  // Inyect the dependencies
  private final CreateBoardUseCase createBoardUseCase;
  private final GetBoardsByOwnerUseCase getBoardsByOwnerUseCase;
  private final BoardWebMapper boardWebMapper;

  // US-105: Create a board
  @PostMapping
  public ResponseEntity<BoardResponse> createBoard (
    @Valid @RequestBody CreateBoardRequest request,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    // Get the email (username) from the token
    String ownerUsername = userDetails.getUsername();
    // Map the dto into a command of domain
    CreateBoardUseCase.CreateBoardCommand command = boardWebMapper.toCommand(request);
    // Call the use case to create the board
    Board createBoard = createBoardUseCase.createBoard(command, ownerUsername);
    // Map the result into response DTO
    return new ResponseEntity<>(boardWebMapper.toResponse(createBoard), HttpStatus.CREATED);
  }

  // US-106: List the boards
  @GetMapping
  public ResponseEntity<List<BoardResponse>> getMyBoards (
    @AuthenticationPrincipal UserDetails userDetails // user of the token
  ) {
    String ownerUsername = userDetails.getUsername();
    // Call the use case to get the boards
    List<Board> boards = getBoardsByOwnerUseCase.getBoards(ownerUsername);
    // Map the list into DTO response and return it
    return ResponseEntity.ok(boardWebMapper.toResponseList(boards));
  }
}
