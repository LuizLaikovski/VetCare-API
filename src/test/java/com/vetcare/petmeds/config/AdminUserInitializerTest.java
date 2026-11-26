package com.vetcare.petmeds.config;

import com.vetcare.petmeds.applicationPrimary.user.entity.TypeUser;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.applicationPrimary.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminUserInitializerTest {

    @Mock
    private UserRepository userRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private AdminUserInitializer initializer(String name, String email, String password) {
        return new AdminUserInitializer(userRepository, encoder, name, email, password);
    }

    @Test
    void run_ShouldCreateAdminWhenMissing() {
        when(userRepository.findByEmail("admin@email.com")).thenReturn(Optional.empty());

        initializer("Administrador", "admin@email.com", "Admin123").run(new DefaultApplicationArguments());

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        UserEntity saved = captor.getValue();
        assertEquals("Administrador", saved.getName());
        assertEquals("admin@email.com", saved.getEmail());
        assertEquals(TypeUser.ADM, saved.getTypeUser());
        assertNull(saved.getImageId());
    }

    @Test
    void run_ShouldStorePasswordHashedNeverInPlainText() {
        when(userRepository.findByEmail("admin@email.com")).thenReturn(Optional.empty());

        initializer("Administrador", "admin@email.com", "Admin123").run(new DefaultApplicationArguments());

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        String stored = captor.getValue().getPassword();
        assertNotEquals("Admin123", stored);
        assertTrue(encoder.matches("Admin123", stored));
    }

    @Test
    void run_ShouldNotCreateOrChangeAnythingWhenAdminAlreadyExists() {
        UserEntity existing = new UserEntity();
        existing.setEmail("admin@email.com");
        existing.setPassword("senha-ja-trocada");
        when(userRepository.findByEmail("admin@email.com")).thenReturn(Optional.of(existing));

        initializer("Administrador", "admin@email.com", "Admin123").run(new DefaultApplicationArguments());

        verify(userRepository, never()).save(any());
        assertEquals("senha-ja-trocada", existing.getPassword());
    }

    @Test
    void run_ShouldUseConfiguredValues() {
        when(userRepository.findByEmail("root@clinica.com")).thenReturn(Optional.empty());

        initializer("Root", "root@clinica.com", "Outra123").run(new DefaultApplicationArguments());

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        assertEquals("root@clinica.com", captor.getValue().getEmail());
        assertTrue(encoder.matches("Outra123", captor.getValue().getPassword()));
    }
}
