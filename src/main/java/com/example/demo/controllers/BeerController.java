package com.example.demo.controllers;

import com.example.demo.jpa.entities.BeerEntity;
import com.example.demo.jpa.repositories.BeerRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BeerController {

  private final BeerRepository beerRepository;

  public BeerController(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;

    BeerEntity beer1 = new BeerEntity(1L, "cerveza trigo", "TAGline1", LocalDate.now(), "description1", 4.5);
    BeerEntity beer2 = new BeerEntity(2L, "cerveza cebada", "TAGline2", LocalDate.now(), "description2", 8.5);
    BeerEntity beer3 = new BeerEntity(3L, "cerveza malta", "TAGline3", LocalDate.now(), "description3", 14.5);

    beerRepository.saveAll(List.of(beer1, beer2, beer3));
  }

  @GetMapping("/beers")
  public Iterable<BeerEntity> getAllBeers(
      @RequestParam(name = "abv_gt", required = false, defaultValue = "0.0") double abvGreaterThan,
      @RequestParam(name = "abv_lt", required = false, defaultValue = "100.0") double abvLessThan) {
    /*if (abvGreaterThan == 0 && abvLessThan == 0) {
      return beerRepository.findAll();
    } else if (abvGreaterThan == 0) {
      return beerRepository.findByAbvLessThanEqual(abvLessThan);
    } else if (abvLessThan == 0) {
      return beerRepository.findByAbvGreaterThanEqual(abvLessThan);
    } else {
      return beerRepository.findByAbvGreaterThanEqualAndAbvLessThanEqual(abvGreaterThan, abvLessThan);
    }*/

    return beerRepository.findByAbvGreaterThanEqualAndAbvLessThanEqual(abvGreaterThan, abvLessThan);
  }

  @PostMapping("/beers")
  public BeerEntity createBeer(@RequestBody BeerEntity beer, HttpServletResponse response) {
    if (beer.getId() == null || beerRepository.findById(beer.getId()).isPresent()) {
      response.setStatus(HttpServletResponse.SC_CONFLICT);
      return beer;
    } else {
      response.setStatus(HttpServletResponse.SC_CREATED);
      return beerRepository.save(beer);
    }
  }

  @PutMapping("/beers")
  public BeerEntity modifyBeer(@RequestBody BeerEntity beer, HttpServletResponse response) {
    if (beer.getId() == null || beerRepository.findById(beer.getId()).isEmpty()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return beer;
    } else {
      response.setStatus(HttpServletResponse.SC_OK);
      return beerRepository.save(beer);
    }
  }

  @DeleteMapping("/beers")
  public BeerEntity deleteBeer(@RequestBody BeerEntity beer, HttpServletResponse response) {
    if (beer.getId() == null || beerRepository.findById(beer.getId()).isEmpty()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return beer;
    } else {
      response.setStatus(HttpServletResponse.SC_OK);
      beerRepository.delete(beer);
      return beer;
    }
  }
}
