package com.example.bookstore.services;

import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.GenreRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

  @Autowired
  private GenreRepository genreRepository;

  public List<Genre> getAllGenres() {
    return genreRepository.findAll();
  }

  public Optional<Genre> getGenreById(Long id) {
    return genreRepository.findById(id);
  }

  public Genre createGenre(Genre genre) {
    return genreRepository.save(genre);
  }

  public Genre updateGenre(Long id, Genre updatedGenre) {
    if (!genreRepository.existsById(id)) {
      throw new RuntimeException("Genre not found");
    }

    updatedGenre.setId(id);
    return genreRepository.save(updatedGenre);
  }

  public void deleteGenre(Long id) {
    if (!genreRepository.existsById(id)) {
      throw new RuntimeException("Genre not found");
    }

    genreRepository.deleteById(id);
  }
}

