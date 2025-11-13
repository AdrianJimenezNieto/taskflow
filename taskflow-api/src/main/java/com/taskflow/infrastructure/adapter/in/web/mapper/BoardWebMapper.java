package com.taskflow.infrastructure.adapter.in.web.mapper;

import com.taskflow.domain.model.Board;
import com.taskflow.domain.port.in.CreateBoardUseCase;
import com.taskflow.infrastructure.adapter.in.web.dto.CreateBoardRequest;
import com.taskflow.infrastructure.adapter.in.web.dto.BoardResponse;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoardWebMapper {
  
  // from DTO to Command of the use case
  public CreateBoardUseCase.CreateBoardCommand toCommand(CreateBoardRequest request) {
    return new CreateBoardUseCase.CreateBoardCommand(request.getTitle());
  }

  // From domain into DTO (Response)
  public BoardResponse toResponse(Board board) {
    return BoardResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .build();
  }

  // From List of Domain into a DTO List (Response)
  public List<BoardResponse> toResponseList(List<Board> boards) {
    return boards.stream()
                  .map(this::toResponse)
                  .collect(Collectors.toList());
  }
}
