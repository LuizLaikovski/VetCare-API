package com.vetcare.petmeds.model.image;

import com.vetcare.petmeds.archives.images.dto.ImageResponseDTO;
import com.vetcare.petmeds.archives.images.entity.ImageEntity;
import com.vetcare.petmeds.archives.images.repository.ImageRepository;
import com.vetcare.petmeds.archives.images.service.ImageService;
import com.vetcare.petmeds.applicationPrimary.user.repository.UserRepository;
import com.vetcare.petmeds.exception.ImageStorageException;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ImageService imageService;

    private ImageEntity image;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        image = new ImageEntity(1L, "foto.png", "image/png", new byte[]{1, 2, 3});
        file = new MockMultipartFile("data", "novo.jpg", "image/jpeg", new byte[]{9, 9});
    }

    @Test
    void uploadImageToDTO_ShouldSaveAndReturnDTO() {
        when(imageRepository.save(any(ImageEntity.class))).thenAnswer(i -> i.getArgument(0));

        ImageResponseDTO result = imageService.uploadImageToDTO(file);

        assertEquals("novo.jpg", result.getName());
        assertEquals("image/jpeg", result.getTypeContent());
        assertArrayEquals(new byte[]{9, 9}, result.getData());
    }

    @Test
    void uploadImageToDTO_ShouldWrapFailuresInImageStorageException() {
        when(imageRepository.save(any())).thenThrow(new RuntimeException("db"));

        assertThrows(ImageStorageException.class, () -> imageService.uploadImageToDTO(file));
    }

    @Test
    void uploadImageToEntity_ShouldReturnSavedEntity() {
        when(imageRepository.save(any(ImageEntity.class))).thenReturn(image);

        assertSame(image, imageService.uploadImageToEntity(file));
    }

    @Test
    void uploadImageToEntity_ShouldThrowWhenFileCannotBeRead() throws IOException {
        MultipartFile broken = mock(MultipartFile.class);
        when(broken.getBytes()).thenThrow(new IOException("io"));

        assertThrows(ImageStorageException.class, () -> imageService.uploadImageToEntity(broken));
    }

    @Test
    void updateImageEntity_ShouldReplaceContent() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        when(imageRepository.save(image)).thenReturn(image);

        ImageEntity result = imageService.updateImageEntity(1L, file);

        assertEquals("novo.jpg", result.getNome());
        assertEquals("image/jpeg", result.getTipoConteudo());
        assertArrayEquals(new byte[]{9, 9}, result.getDados());
    }

    @Test
    void updateImageEntity_ShouldThrowNotFoundWhenMissing() {
        when(imageRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> imageService.updateImageEntity(2L, file));
        verify(imageRepository, never()).save(any());
    }

    @Test
    void updateImageEntity_ShouldWrapOtherFailures() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        when(imageRepository.save(any())).thenThrow(new RuntimeException("db"));

        assertThrows(ImageStorageException.class, () -> imageService.updateImageEntity(1L, file));
    }

    @Test
    void findImageByIdToDTO_ShouldReturnDTO() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        assertEquals("foto.png", imageService.findImageByIdToDTO(1L).getName());
    }

    @Test
    void findImageByIdToDTO_ShouldThrowNotFound() {
        when(imageRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> imageService.findImageByIdToDTO(2L));
    }

    @Test
    void findImageByIdToEntity_ShouldReturnEntityOrThrow() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        when(imageRepository.findById(2L)).thenReturn(Optional.empty());

        assertSame(image, imageService.findImageByIdToEntity(1L));
        assertThrows(ResourceNotFoundException.class, () -> imageService.findImageByIdToEntity(2L));
    }

    @Test
    void findByIdToImage_ShouldReturnBinaryWithHeaders() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        ResponseEntity<Resource> response = imageService.findByIdToImage(1L);

        assertEquals(MediaType.IMAGE_PNG, response.getHeaders().getContentType());
        assertEquals(3, response.getHeaders().getContentLength());
        assertTrue(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("foto.png"));
        assertNotNull(response.getBody());
    }

    @Test
    void findByIdToImage_ShouldFallbackToOctetStreamWhenTypeInvalid() {
        image.setTipoConteudo("tipo-invalido");
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        assertEquals(MediaType.APPLICATION_OCTET_STREAM, imageService.findByIdToImage(1L).getHeaders().getContentType());
    }

    @Test
    void findByIdToImage_ShouldThrowNotFound() {
        when(imageRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> imageService.findByIdToImage(2L));
    }

    @Test
    void deletedImage_ShouldDeleteEntity() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        imageService.deletedImage(1L);

        verify(imageRepository).delete(image);
    }

    @Test
    void deletedImage_ShouldThrowStorageExceptionWhenMissing() {
        when(imageRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ImageStorageException.class, () -> imageService.deletedImage(2L));
    }
}
