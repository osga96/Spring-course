package com.example.demo.jpa.repositories;

import com.example.demo.jpa.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

  public UserEntity findByEmail(String email);

}
