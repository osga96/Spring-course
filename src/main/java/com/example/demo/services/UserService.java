package com.example.demo.services;

import com.example.demo.jpa.entities.UserEntity;
import com.example.demo.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    userRepository.save(new UserEntity(1L, "test1", "test1apellido", "test@test.test"));
  }

  public Iterable<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  public UserEntity saveUser(UserEntity user) {
    return userRepository.save(user);
  }

  public void deleteUser(long id) {
    userRepository.deleteById(id);
  }

}
