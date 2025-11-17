package com.taskflow.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTaskListRequest {
  @NotBlank(message = "El título es obligatorio")
  private String title;
}
