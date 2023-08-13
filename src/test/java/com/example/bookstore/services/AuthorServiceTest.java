package com.example.bookstore.services;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
  @InjectMocks
  private AuthorService authorService;

  @Mock
  private AuthorRepository authorRepository;


  @Test
  public void testGetAllAuthors() {
    Author author1 = new Author();
    author1.setId(1L);
    Author author2 = new Author();
    author2.setId(2L);

    when(authorRepository.findAll()).thenReturn(List.of(author1, author2));

    List<Author> authors = authorService.getAllAuthors();

    assertEquals(2, authors.size());
  }

  @Test
  public void testGetAuthorById_Exists() {
    Author author = new Author();
    author.setId(1L);

    when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

    Optional<Author> retrievedAuthor = authorService.getAuthorById(1L);

    assertTrue(retrievedAuthor.isPresent());
    assertEquals(1L, retrievedAuthor.get().getId());
  }

  @Test
  public void testGetAuthorById_NotExists() {
    when(authorRepository.findById(1L)).thenReturn(Optional.empty());

    Optional<Author> retrievedAuthor = authorService.getAuthorById(1L);

    assertFalse(retrievedAuthor.isPresent());
  }

  @Test
  public void testCreateAuthor() {
    Author newAuthor = new Author();
    newAuthor.setName("John Doe");

    when(authorRepository.save(any(Author.class))).thenReturn(newAuthor);

    Author createdAuthor = authorService.createAuthor(newAuthor);

    assertEquals("John Doe", createdAuthor.getName());
  }

  @Test
  public void testUpdateAuthor_Success() {
    Author existingAuthor = new Author();
    existingAuthor.setId(1L);
    existingAuthor.setName("Jane Smith");

    Author updatedAuthor = new Author();
    updatedAuthor.setId(1L);
    updatedAuthor.setName("Updated Name");

    when(authorRepository.existsById(1L)).thenReturn(true);
    when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

    Author result = authorService.updateAuthor(1L, updatedAuthor);

    assertEquals("Updated Name", result.getName());
  }

  @Test
  public void testUpdateAuthor_NotFound() {
    when(authorRepository.existsById(1L)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> authorService.updateAuthor(1L, new Author()));
  }

  @Test
  public void testDeleteAuthor_Success() {
    when(authorRepository.existsById(1L)).thenReturn(true);

    authorService.deleteAuthor(1L);

    // Verify that deleteById method was called once with correct ID
    verify(authorRepository, times(1)).deleteById(1L);
  }

  @Test
  public void testDeleteAuthor_NotFound() {
    when(authorRepository.existsById(1L)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> authorService.deleteAuthor(1L));
  }

}
