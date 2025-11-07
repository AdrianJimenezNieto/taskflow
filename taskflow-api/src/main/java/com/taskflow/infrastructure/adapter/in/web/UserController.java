package com.taskflow.infrastructure.adapter.in.web;

import com.taskflow.domain.model.User;
import com.taskflow.domain.port.in.RegisterUserUseCase;
import com.taskflow.infrastructure.adapter.in.web.dto.RegisterUserRequest;
import com.taskflow.infrastructure.adapter.in.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/v1/users") // Base URL for user-related endpoints
@RequiredArgsConstructor
public class UserController {
  
  // We depend only on the in port (RegisterUserUseCase)
  private final RegisterUserUseCase registerUserUseCase;

  private final UserWebMapper userWebMapper; // Mapper for converting between DTOs and domain models

  @PostMapping("/register") // Endpoint for user registration
  // Valid activates validation on the incoming request body
  public ResponseEntity<User> register(@Valid @RequestBody RegisterUserRequest request) {

    // Map DTO to domain model
    User userToRegister = userWebMapper.toDomain(request);

    // Call the use case to register the user
    User registeredUser = registerUserUseCase.registerUser(userToRegister);

    // Return the response (201 Created)
    return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
  }
}
