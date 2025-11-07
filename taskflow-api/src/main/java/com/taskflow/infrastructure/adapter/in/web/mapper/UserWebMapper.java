package com.taskflow.infrastructure.adapter.in.web.mapper;

import com.taskflow.domain.model.User;
import com.taskflow.infrastructure.adapter.in.web.dto.RegisterUserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {
  
  // Converts RegisterUserRequest DTO to User domain model
  public User toDomain(RegisterUserRequest request) {

    return User.builder()
            .userName(request.getUserName())
            .name(request.getName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(request.getPassword())
            .build();
  }

  // TODO: Method to map User domain model to a response DTO to avoid returning passwords
}
