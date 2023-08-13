package com.example.bookstore.services;

import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
  @Autowired
  private AuthorRepository authorRepository;

  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  public Optional<Author> getAuthorById(Long id) {
    return authorRepository.findById(id);
  }

  public Author createAuthor(Author author) {
    return authorRepository.save(author);
  }

  public Author updateAuthor(Long id, Author updatedAuthor) {
    if (!authorRepository.existsById(id)) {
      throw new RuntimeException("Author not found");
    }

    updatedAuthor.setId(id);
    return authorRepository.save(updatedAuthor);
  }

  public void deleteAuthor(Long id) {
    if (!authorRepository.existsById(id)) {
      throw new RuntimeException("Author not found");
    }

    authorRepository.deleteById(id);
  }
}

