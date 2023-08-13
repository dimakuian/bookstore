package com.example.bookstore.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.bookstore.model.Author;
import com.example.bookstore.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthorService authorService;

  @Test
  public void testCreateAuthor() throws Exception {
    Author newAuthor = new Author();
    newAuthor.setId(1L);
    newAuthor.setName("John Doe");

    when(authorService.createAuthor(any(Author.class))).thenReturn(newAuthor);

    mockMvc.perform(post("/api/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newAuthor)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John Doe"));
  }

  @Test
  public void testUpdateAuthor_Success() throws Exception {
    Author updatedAuthor = new Author();
    updatedAuthor.setId(1L);
    updatedAuthor.setName("Updated Name");

    when(authorService.updateAuthor(eq(1L), any(Author.class))).thenReturn(updatedAuthor);

    mockMvc.perform(put("/api/authors/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updatedAuthor)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Name"));
  }

  @Test
  public void testUpdateAuthor_NotFound() throws Exception {
    when(authorService.updateAuthor(eq(1L), any(Author.class))).thenThrow(new RuntimeException("Author not found"));

    mockMvc.perform(put("/api/authors/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(new Author())))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteAuthor_Success() throws Exception {
    mockMvc.perform(delete("/api/authors/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Author deleted successfully"));
  }

  @Test
  public void testDeleteAuthor_NotFound() throws Exception {
    doThrow(new RuntimeException("Author not found")).when(authorService).deleteAuthor(1L);

    mockMvc.perform(delete("/api/authors/1"))
        .andExpect(status().isNotFound());
  }

}
