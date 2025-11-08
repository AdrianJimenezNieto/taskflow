package com.taskflow.infrastructure.adapter.in.web.mapper;

import com.taskflow.domain.model.User;
import com.taskflow.infrastructure.adapter.in.web.dto.RegisterUserRequest;
import com.taskflow.infrastructure.adapter.in.web.dto.UserResponse;
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

  public UserResponse toResponse(User domain) {
    UserResponse response = new UserResponse();
    response.setId(domain.getId());
    response.setUserName(domain.getUserName());
    response.setName(domain.getName());
    response.setLastName(domain.getLastName());
    response.setEmail(domain.getEmail());
    return response;
  }
}
