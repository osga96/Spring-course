package com.example.demo.controllers;

import com.example.demo.jpa.entities.UserEntity;
import com.example.demo.jpa.repositories.UserRepository;
import com.example.demo.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  @GetMapping("/users")
  public Iterable<UserEntity> getUser() {
    return userService.getAllUsers();
  }

  @PostMapping("/users")
  public UserEntity addUser(@RequestBody UserEntity user, HttpServletResponse response) {
    UserEntity userDb = userRepository.findByEmail(user.getEmail());
    if (userDb == null) {
      response.setStatus(HttpServletResponse.SC_CREATED);
      return userRepository.save(user);
    } else {
      response.setStatus(HttpServletResponse.SC_CONFLICT);
      return user;
    }
  }

  @PutMapping("/users")
  public UserEntity updateUser(@RequestBody UserEntity user, HttpServletResponse response) {
    UserEntity userDb = userRepository.findByEmail(user.getEmail());
    if (userDb != null) {
      response.setStatus(HttpServletResponse.SC_OK);
      return userRepository.save(user);
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return user;
    }
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
  }

  @DeleteMapping("/users/")
  public UserEntity deleteUserByEmail(@RequestParam String email, HttpServletResponse response) {
    UserEntity userDb = userRepository.findByEmail(email);
    if (userDb != null) {
      response.setStatus(HttpServletResponse.SC_OK);
      userRepository.delete(userDb);
      return userDb;
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return new UserEntity("", "", email);
    }
  }
}
