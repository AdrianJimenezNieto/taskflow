package com.taskflow.infrastructure.adapter.out.persistence;

import com.taskflow.domain.model.User;
import com.taskflow.domain.port.out.UserRepositoryPort;
import com.taskflow.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.taskflow.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.taskflow.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor; // Constructor injection
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // Spring Bean
@RequiredArgsConstructor // Constructor injection
public class UserPersistenceAdapter implements UserRepositoryPort {

  // Depends on JPA repository and Mapper
  private final UserJpaRepository userJpaRepository;
  private final UserPersistenceMapper userMapper;

  // Implements methods from UserRepositoryPort
  
  @Override
  public User save(User user) {
    // Translate Domain Model to Entity
    UserEntity userEntity = userMapper.toEntity(user);
    // Save Entity using JPA repository
    UserEntity savedEntity = userJpaRepository.save(userEntity);
    // Translate back to Domain Model and return
    return userMapper.toDomain(savedEntity);
  }

  @Override
  
}
