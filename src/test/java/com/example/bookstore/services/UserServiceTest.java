package com.example.bookstore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Test
  public void testRegisterUser_Success() {
    User user = new User();
    user.setUsername("testuser");
    user.setPassword("password");

    when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    User registeredUser = userService.registerUser(user);

    assertNotNull(registeredUser);
    assertEquals("testuser", registeredUser.getUsername());
    // Add more assertions as needed
  }

  @Test
  public void testRegisterUser_UsernameExists() {
    User user = new User();
    user.setUsername("existinguser");
    user.setPassword("password");

    when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(user));

    assertThrows(RuntimeException.class, () -> userService.registerUser(user));
  }
}
