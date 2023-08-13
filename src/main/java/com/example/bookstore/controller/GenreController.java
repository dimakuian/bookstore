package com.example.bookstore.controller;

import com.example.bookstore.model.Genre;
import com.example.bookstore.services.GenreService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

  @Autowired
  private GenreService genreService;

  @GetMapping
  public List<Genre> getAllGenres() {
    return genreService.getAllGenres();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
    Optional<Genre> genre = genreService.getGenreById(id);
    return genre.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
    Genre createdGenre = genreService.createGenre(genre);
    return ResponseEntity.ok(createdGenre);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre updatedGenre) {
    try {
      Genre genre = genreService.updateGenre(id, updatedGenre);
      return ResponseEntity.ok(genre);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteGenre(@PathVariable Long id) {
    try {
      genreService.deleteGenre(id);
      return ResponseEntity.ok("Genre deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}

