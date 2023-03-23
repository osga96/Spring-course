package com.example.demo.jpa.repositories;

import com.example.demo.jpa.entities.BeerEntity;
import com.example.demo.jpa.entities.UserEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends CrudRepository<BeerEntity, Long> {

  public Iterable<BeerEntity> findByAbvGreaterThanEqual(double abv);

  public Iterable<BeerEntity> findByAbvLessThanEqual(double abv);

  public Iterable<BeerEntity> findByAbvGreaterThanEqualAndAbvLessThanEqual(double abvGt, double abvLt);

}
