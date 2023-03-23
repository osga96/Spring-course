package com.example.demo.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BeerEntity {

  @Id
  private Long id;

  private String name;

  private String tagline;

  private LocalDate first_brewed;

  private String description;

  private double abv;

  public BeerEntity(String name, String tagline, LocalDate first_brewed, String description,
      double abv) {
    this.name = name;
    this.tagline = tagline;
    this.first_brewed = first_brewed;
    this.description = description;
    this.abv = abv;
  }
}
