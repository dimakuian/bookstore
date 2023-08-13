package com.example.bookstore.controller;

import com.example.bookstore.model.Author;
import com.example.bookstore.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
  @Autowired
  private AuthorService authorService;

  @GetMapping
  public List<Author> getAllAuthors() {
    return authorService.getAllAuthors();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
    Optional<Author> author = authorService.getAuthorById(id);
    return author.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
    Author createdAuthor = authorService.createAuthor(author);
    return ResponseEntity.ok(createdAuthor);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
    try {
      Author author = authorService.updateAuthor(id, updatedAuthor);
      return ResponseEntity.ok(author);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
    try {
      authorService.deleteAuthor(id);
      return ResponseEntity.ok("Author deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
