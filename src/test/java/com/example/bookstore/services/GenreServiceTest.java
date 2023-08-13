package com.example.bookstore.services;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {
  @InjectMocks
  private GenreService genreService;

  @Mock
  private GenreRepository genreRepository;

  @Test
  public void testCreateGenre() {
    Genre newGenre = new Genre();
    newGenre.setName("Mystery");

    when(genreRepository.save(any(Genre.class))).thenReturn(newGenre);

    Genre createdGenre = genreService.createGenre(newGenre);

    assertEquals("Mystery", createdGenre.getName());
  }

  @Test
  public void testUpdateGenre_Success() {
    Genre existingGenre = new Genre();
    existingGenre.setId(1L);
    existingGenre.setName("Adventure");

    Genre updatedGenre = new Genre();
    updatedGenre.setId(1L);
    updatedGenre.setName("Updated Genre");

    when(genreRepository.existsById(1L)).thenReturn(true);
    when(genreRepository.save(any(Genre.class))).thenReturn(updatedGenre);

    Genre result = genreService.updateGenre(1L, updatedGenre);

    assertEquals("Updated Genre", result.getName());
  }

  @Test
  public void testUpdateGenre_NotFound() {
    when(genreRepository.existsById(1L)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> genreService.updateGenre(1L, new Genre()));
  }

  @Test
  public void testDeleteGenre_Success() {
    when(genreRepository.existsById(1L)).thenReturn(true);

    genreService.deleteGenre(1L);

    verify(genreRepository, times(1)).deleteById(1L);
  }

  @Test
  public void testDeleteGenre_NotFound() {
    when(genreRepository.existsById(1L)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> genreService.deleteGenre(1L));
  }

}
