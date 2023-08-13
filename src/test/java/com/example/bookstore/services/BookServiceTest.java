package com.example.bookstore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

  @InjectMocks
  private BookService bookService;

  @Mock
  private BookRepository bookRepository;

  @Test
  public void testGetAllBooks() {
    Book book1 = new Book();
    book1.setId(1L);
    Book book2 = new Book();
    book2.setId(2L);

    when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

    List<Book> books = bookService.getAllBooks();

    assertEquals(2, books.size());
  }

  @Test
  public void testGetBookById_Exists() {
    Book book = new Book();
    book.setId(1L);

    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

    Optional<Book> retrievedBook = bookService.getBookById(1L);

    assertTrue(retrievedBook.isPresent());
    assertEquals(1L, retrievedBook.get().getId());
  }

  @Test
  public void testGetBookById_NotExists() {
    when(bookRepository.findById(1L)).thenReturn(Optional.empty());

    Optional<Book> retrievedBook = bookService.getBookById(1L);

    assertFalse(retrievedBook.isPresent());
  }

  @Test
  public void testCreateBook() {
    Book newBook = new Book();
    newBook.setTitle("The Great Gatsby");

    when(bookRepository.save(any(Book.class))).thenReturn(newBook);

    Book createdBook = bookService.createBook(newBook);

    assertEquals("The Great Gatsby", createdBook.getTitle());
  }

  @Test
  public void testUpdateBook_Success() {
    Book existingBook = new Book();
    existingBook.setId(1L);
    existingBook.setTitle("To Kill a Mockingbird");

    Book updatedBook = new Book();
    updatedBook.setId(1L);
    updatedBook.setTitle("Updated Title");

    when(bookRepository.existsById(1L)).thenReturn(true);
    when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

    Book result = bookService.updateBook(1L, updatedBook);

    assertEquals("Updated Title", result.getTitle());
  }

  @Test
  public void testUpdateBook_NotFound() {
    when(bookRepository.existsById(1L)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> bookService.updateBook(1L, new Book()));
  }

  @Test
  public void testDeleteBook_Success() {
    when(bookRepository.existsById(1L)).thenReturn(true);

    bookService.deleteBook(1L);

    verify(bookRepository, times(1)).deleteById(1L);
  }

  @Test
  public void testDeleteBook_NotFound() {
    when(bookRepository.existsById(1L)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> bookService.deleteBook(1L));
  }

}
