package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.archives.images.controller.ImageController;
import com.vetcare.petmeds.archives.images.dto.ImageResponseDTO;
import com.vetcare.petmeds.archives.images.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ImageController.class,
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ImageService imageService;

    @Test
    void upload_ShouldReturnCreated() throws Exception {
        when(imageService.uploadImageToDTO(any())).thenReturn(new ImageResponseDTO("a.png", "image/png", new byte[]{1}));

        mockMvc.perform(multipart("/images").file(new MockMultipartFile("data", "a.png", "image/png", new byte[]{1})))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("a.png"));
    }

    @Test
    void findMetadata_ShouldReturnOk() throws Exception {
        when(imageService.findImageByIdToDTO(1L)).thenReturn(new ImageResponseDTO("a.png", "image/png", new byte[]{1}));

        mockMvc.perform(get("/images/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeContent").value("image/png"));
    }

    @Test
    void findFile_ShouldReturnBinaryOnFileSubresource() throws Exception {
        when(imageService.findByIdToImage(1L)).thenReturn(ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG).body(new ByteArrayResource(new byte[]{5})));

        mockMvc.perform(get("/images/1/file"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{5}));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/images/1")).andExpect(status().isNoContent());

        verify(imageService).deletedImage(1L);
    }

    @Test
    void oldFileRoute_ShouldNotExist() throws Exception {
        mockMvc.perform(get("/image/file/1")).andExpect(status().is4xxClientError());
    }
}
