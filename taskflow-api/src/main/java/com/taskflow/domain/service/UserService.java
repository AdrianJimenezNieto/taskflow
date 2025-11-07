package com.taskflow.domain.service;

import com.taskflow.domain.model.User;
import com.taskflow.domain.port.in.RegisterUserUseCase;
import com.taskflow.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// User Service implements the in port
// Here stands the buisness logic for user-related operations
@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase {
  
  // We depend on the out port (UserRepositoryPort), not the interface
  private final UserRepositoryPort userRepositoryPort;

  @Override
  public User registerUser(User user) {

    // Verify if the email is already taken
    if (userRepositoryPort.existsByEmail(user.getEmail())) {
      // TODO: throw custom exception
      throw new IllegalArgumentException("El email ya está en uso.");
    }

    // TODO: Password hashing logic should be here
    // For now, we will save it as plain text (not recommended for production)

    // Persist the user using the out port
    return userRepositoryPort.save(user);
  }
}
