package com.vetcare.petmeds.model.image;

import com.vetcare.petmeds.applicationPrimary.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserProfileDTO;
import com.vetcare.petmeds.applicationPrimary.user.entity.TypeUser;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.applicationPrimary.user.repository.UserRepository;
import com.vetcare.petmeds.applicationPrimary.user.service.UserService;
import com.vetcare.petmeds.applicationPrimary.token.service.TokenService;
import com.vetcare.petmeds.archives.images.entity.ImageEntity;
import com.vetcare.petmeds.archives.images.service.ImageService;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** A imagem do usuário é opcional: cadastro, login e perfil funcionam sem ela. */
@ExtendWith(MockitoExtension.class)
public class OptionalImageUserTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ImageService imageService;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("hash");
        user.setTypeUser(TypeUser.CLIENT);
    }

    @Test
    void newUser_ShouldNotRequireImage() {
        when(passwordEncoder.encode("123")).thenReturn("hash");

        ResponseLoginDTO response = userService.newUser(new UserDTO("John", "john@example.com", "123", null));

        assertNotNull(response);
        verifyNoInteractions(imageService);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void login_ShouldWorkForUserWithoutImage() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123", "hash")).thenReturn(true);
        when(tokenService.generateAndStoreToken(user)).thenReturn("token");

        ResponseLoginDTO response = userService.login("john@example.com", "123");

        assertEquals("token", response.getToken());
        assertNull(response.getUser().getImage());
        assertEquals("john@example.com", response.getUser().getEmail());
        verify(imageService, never()).findImageByIdToEntity(any());
    }

    @Test
    void login_ShouldReturnImageWhenUserHasOne() {
        user.setImageId(5L);
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123", "hash")).thenReturn(true);
        when(tokenService.generateAndStoreToken(user)).thenReturn("token");
        when(imageService.findImageByIdToEntity(5L)).thenReturn(new ImageEntity(5L, "a.png", "image/png", new byte[]{1, 2}));

        ResponseLoginDTO response = userService.login("john@example.com", "123");

        assertArrayEquals(new byte[]{1, 2}, response.getUser().getImage());
    }

    @Test
    void login_ShouldNotFailWhenReferencedImageNoLongerExists() {
        user.setImageId(5L);
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123", "hash")).thenReturn(true);
        when(tokenService.generateAndStoreToken(user)).thenReturn("token");
        when(imageService.findImageByIdToEntity(5L)).thenThrow(new ResourceNotFoundException("Imagem não encontrada!"));

        ResponseLoginDTO response = userService.login("john@example.com", "123");

        assertNull(response.getUser().getImage());
    }

    @Test
    void getUserByIdToDTO_ShouldWorkForUserWithoutImage() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserProfileDTO dto = userService.getUserByIdToDTO(1L);

        assertEquals("John Doe", dto.getName());
        assertNull(dto.getImage());
    }

    @Test
    void getByImage_ShouldReturn404WithClearMessageWhenUserHasNoImage() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> userService.getByImage(1L));

        assertEquals("Usuário não possui imagem cadastrada!", ex.getMessage());
    }

    @Test
    void getByImage_ShouldKeepUserNotFoundMessageForUnknownUser() {
        when(userRepository.findById(9L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> userService.getByImage(9L));

        assertEquals("Usuário não encontrado!", ex.getMessage());
    }
}
