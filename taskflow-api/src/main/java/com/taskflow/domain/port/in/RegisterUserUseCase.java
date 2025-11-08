package com.taskflow.domain.port.in;

import com.taskflow.domain.model.User;

// In  Port: defines the use case for registering a user
public interface RegisterUserUseCase {
  
  User registerUser(User user);
}
