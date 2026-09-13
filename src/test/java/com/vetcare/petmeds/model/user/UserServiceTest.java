package com.vetcare.petmeds.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.exception.UnauthorizedException;
import com.vetcare.petmeds.modules.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.modules.user.dto.UserDTO;
import com.vetcare.petmeds.modules.user.entity.TypeUser;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import com.vetcare.petmeds.modules.user.repository.UserRepository;
import com.vetcare.petmeds.modules.user.service.UserService;

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

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("hashedPassword"); // Mocked hash
        user.setTypeUser(TypeUser.CLIENT);
    }

    @Test
    void newUser_ShouldReturnSuccessResponseWithDefaultType() {
        UserDTO userDTO = new UserDTO("John Doe", "john@example.com", "password123", null);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        userService.newUser(userDTO);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());

        UserEntity savedUser = captor.getValue();
        assertEquals("hashedPassword", savedUser.getPassword());
        assertEquals(TypeUser.CLIENT, savedUser.getTypeUser());
        assertEquals("John Doe", savedUser.getName());
    }

    @Test
    void newUser_ShouldReturnSuccessResponseWithProvidedType() {
        UserDTO userDTO = new UserDTO("John Doe", "john@example.com", "password123", TypeUser.ADM);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        userService.newUser(userDTO);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());

        UserEntity savedUser = captor.getValue();
        assertEquals("hashedPassword", savedUser.getPassword());
        assertEquals(TypeUser.ADM, savedUser.getTypeUser());
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        Page<UserEntity> userPage = new PageImpl<>(Arrays.asList(user));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        Pageable pageable = PageRequest.of(0, 10);

        Page<UserEntity> result = userService.findAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("John Doe", result.getContent().get(0).getName());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void getUserById_ShouldReturnUserWhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldThrowExceptionWhenNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void login_ShouldThrowExceptionWhenEmailNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.login("unknown@example.com", "password123"));
    }

    @Test
    void login_ShouldThrowExceptionWhenPasswordIncorrect() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "hashedPassword")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> userService.login("john@example.com", "wrongpassword"));
    }

    @Test
    void updateUser_ShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, new UserEntity()));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void deleteUser_ShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void deleteUser_ShouldReturnSuccess() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        ResponseLoginDTO response = userService.deleteUser(1L);

        assertEquals("O usuario foi removido com sucesso!", response.getResponse());
        verify(userRepository, times(1)).deleteById(1L);
    }
}
