package com.example.bookstore.services;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  @Autowired
  private BookRepository bookRepository;

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> getBookById(Long id) {
    return bookRepository.findById(id);
  }

  public Book createBook(Book book) {
    return bookRepository.save(book);
  }

  public Book updateBook(Long id, Book updatedBook) {
    if (!bookRepository.existsById(id)) {
      throw new RuntimeException("Book not found");
    }

    updatedBook.setId(id);
    return bookRepository.save(updatedBook);
  }

  public void deleteBook(Long id) {
    if (!bookRepository.existsById(id)) {
      throw new RuntimeException("Book not found");
    }

    bookRepository.deleteById(id);
  }
}

