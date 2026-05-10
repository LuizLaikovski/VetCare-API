package com.vetcare.petmeds.service;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.User;
import com.vetcare.petmeds.repository.UserRepository;
import com.vetcare.petmeds.utilities.TypeUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        user.setTypeUser(TypeUser.CLIENT);
    }

    @Test
    void newUser_ShouldReturnSuccessResponse() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseDTO response = userService.newUser(user);

        assertEquals("Usuario Criado com sucesso!", response.getResponse());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUserWhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldReturnNullWhenNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User result = userService.getUserById(1L);

        assertNull(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserByEmail_ShouldReturnUserWhenExists() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("john@example.com");

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    @Test
    void login_ShouldReturnSuccessWhenCredentialsAreCorrect() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        ResponseDTO response = userService.login("john@example.com", "password123");

        assertEquals("Login realizado com sucesso!", response.getResponse());
    }

    @Test
    void login_ShouldReturnErrorWhenEmailNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        ResponseDTO response = userService.login("unknown@example.com", "password123");

        assertEquals("Este email não possui cadastro!", response.getResponse());
    }

    @Test
    void login_ShouldReturnErrorWhenPasswordIncorrect() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        ResponseDTO response = userService.login("john@example.com", "wrongpassword");

        assertEquals("Senha Incorreta!", response.getResponse());
    }

    @Test
    void updateUser_ShouldReturnSuccessWhenUserExists() {
        User updatedInfo = new User();
        updatedInfo.setName("John Updated");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseDTO response = userService.updateUser(1L, updatedInfo);

        assertEquals("O usuario foi atualizado com sucesso!", response.getResponse());
        assertEquals("John Updated", user.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_ShouldReturnErrorWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseDTO response = userService.updateUser(1L, new User());

        assertEquals("Usuário não encontrado!", response.getResponse());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldReturnSuccess() {
        doNothing().when(userRepository).deleteById(1L);

        ResponseDTO response = userService.deleteUser(1L);

        assertEquals("O usuario foi removido com sucesso!", response.getResponse());
        verify(userRepository, times(1)).deleteById(1L);
    }
}
