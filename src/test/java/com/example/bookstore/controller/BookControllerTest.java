package com.example.bookstore.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.bookstore.model.Book;
import com.example.bookstore.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
public class BookControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  @Test
  public void testCreateBook() throws Exception {
    Book newBook = new Book();
    newBook.setId(1L);
    newBook.setTitle("The Great Gatsby");

    when(bookService.createBook(any(Book.class))).thenReturn(newBook);

    mockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newBook)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("The Great Gatsby"));
  }

  @Test
  public void testUpdateBook_Success() throws Exception {
    Book updatedBook = new Book();
    updatedBook.setId(1L);
    updatedBook.setTitle("Updated Title");

    when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);

    mockMvc.perform(put("/api/books/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updatedBook)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Updated Title"));
  }

  @Test
  public void testUpdateBook_NotFound() throws Exception {
    when(bookService.updateBook(eq(1L), any(Book.class))).thenThrow(new RuntimeException("Book not found"));

    mockMvc.perform(put("/api/books/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(new Book())))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteBook_Success() throws Exception {
    mockMvc.perform(delete("/api/books/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Book deleted successfully"));
  }

  @Test
  public void testDeleteBook_NotFound() throws Exception {
    doThrow(new RuntimeException("Book not found")).when(bookService).deleteBook(1L);

    mockMvc.perform(delete("/api/books/1"))
        .andExpect(status().isNotFound());
  }
}
