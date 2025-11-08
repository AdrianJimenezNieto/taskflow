package com.taskflow.domain.service;

import com.taskflow.domain.model.User;
import com.taskflow.domain.port.in.RegisterUserUseCase;
import com.taskflow.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

// Exceptions
import com.taskflow.domain.exception.EmailAlreadyExistsException;

// User Service implements the in port
// Here stands the buisness logic for user-related operations
@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase {
  
  // We depend on the out port (UserRepositoryPort), not the interface
  private final UserRepositoryPort userRepositoryPort;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User registerUser(User user) {

    // Verify if the email is already taken
    if (userRepositoryPort.existsByEmail(user.getEmail())) {
      throw new EmailAlreadyExistsException("El email " + user.getEmail() + " ya está registrado.");
    }

    // Hash the password before saving
    String hashedPassword = passwordEncoder.encode(user.getPassword());

    // Set the hashed password to the user object
    user.setPassword(hashedPassword);

    // Persist the user using the out port
    return userRepositoryPort.save(user);
  }
}
