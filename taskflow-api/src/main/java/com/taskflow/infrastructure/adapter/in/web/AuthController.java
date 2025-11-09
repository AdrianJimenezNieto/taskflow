package com.taskflow.infrastructure.adapter.in.web;

import com.taskflow.domain.model.User;
import com.taskflow.domain.port.in.LoginUserUseCase;
import com.taskflow.domain.port.in.RegisterUserUseCase;
import com.taskflow.infrastructure.adapter.in.web.dto.JwtAuthenticationResponse;
import com.taskflow.infrastructure.adapter.in.web.dto.LoginRequest;
import com.taskflow.infrastructure.adapter.in.web.dto.RegisterUserRequest;
import com.taskflow.infrastructure.adapter.in.web.dto.UserResponse;
import com.taskflow.infrastructure.adapter.in.web.mapper.UserWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth") // New base URL for authentication
@RequiredArgsConstructor
public class AuthController {
  
  private final RegisterUserUseCase registerUserUseRequest;
  private final LoginUserUseCase loginUserUseCase;
  private final UserWebMapper userWebMapper;

  // We move the /register here
  @PostMapping("/register") // Endpoint for user registration
  // Valid activates validation on the incoming request body
  public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
    // Map DTO to domain model
    User userToRegister = userWebMapper.toDomain(request);
    // Call the use case to register the user
    User registeredUser = registerUserUseRequest.registerUser(userToRegister);
    // Map the User domain to DTO safe response
    UserResponse response = userWebMapper.toResponse(registeredUser);
    // Return the response (201 Created)
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  // Login method
  @PostMapping("/login")
  public ResponseEntity<JwtAuthenticationResponse> login (@Valid @RequestBody LoginRequest request) {
    // Create the domain "command"
    LoginUserUseCase.LoginCommand command = 
      new LoginUserUseCase.LoginCommand(
        request.getEmail(),
        request.getPassword()
      );

    // Call the use case
    String jwt = loginUserUseCase.login(command);

    // Return the token
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }
}
