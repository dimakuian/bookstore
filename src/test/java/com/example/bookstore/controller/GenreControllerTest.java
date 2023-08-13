package com.example.bookstore.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.bookstore.model.Genre;
import com.example.bookstore.services.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GenreService genreService;

  @Test
  public void testCreateGenre() throws Exception {
    Genre newGenre = new Genre();
    newGenre.setId(1L);
    newGenre.setName("Mystery");

    when(genreService.createGenre(any(Genre.class))).thenReturn(newGenre);

    mockMvc.perform(post("/api/genres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newGenre)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Mystery"));
  }

  @Test
  public void testUpdateGenre_Success() throws Exception {
    Genre updatedGenre = new Genre();
    updatedGenre.setId(1L);
    updatedGenre.setName("Updated Genre");

    when(genreService.updateGenre(eq(1L), any(Genre.class))).thenReturn(updatedGenre);

    mockMvc.perform(put("/api/genres/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updatedGenre)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Genre"));
  }

  @Test
  public void testUpdateGenre_NotFound() throws Exception {
    when(genreService.updateGenre(eq(1L), any(Genre.class))).thenThrow(new RuntimeException("Genre not found"));

    mockMvc.perform(put("/api/genres/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(new Genre())))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteGenre_Success() throws Exception {
    mockMvc.perform(delete("/api/genres/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Genre deleted successfully"));
  }

  @Test
  public void testDeleteGenre_NotFound() throws Exception {
    doThrow(new RuntimeException("Genre not found")).when(genreService).deleteGenre(1L);

    mockMvc.perform(delete("/api/genres/1"))
        .andExpect(status().isNotFound());
  }
}
