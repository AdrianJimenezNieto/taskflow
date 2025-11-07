package com.taskflow.domain.port.out;

import com.taskflow.domain.model.User;
import java.util.Optional;

// Port interface for User repository operations
public interface UserRepositoryPort {
  
  User save(User user);

  Optional<User> findByEmail(String email);

  Optional<User> findById(Long id);

  boolean existsByEmail(String email);

  // TODO: Add 'delete', 'update',...
}
