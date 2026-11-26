package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.applicationPrimary.animal.controller.AnimalController;
import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalSimpleDescriptionDTO;
import com.vetcare.petmeds.applicationPrimary.animal.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Rotas de imagem do animal: /animals/{id}/image. */
@WebMvcTest(controllers = AnimalController.class,
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class AnimalImageRoutesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnimalService animalService;

    private MockMultipartFile image() {
        return new MockMultipartFile("image", "rex.png", MediaType.IMAGE_PNG_VALUE, new byte[]{1, 2, 3});
    }

    @Test
    void setImage_ShouldReturnOk() throws Exception {
        AnimalSimpleDescriptionDTO dto = new AnimalSimpleDescriptionDTO();
        dto.setId(1L);
        dto.setName("Rex");
        when(animalService.setImage(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(multipart("/animals/1/image").file(image()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rex"));
    }

    @Test
    void getImage_ShouldReturnBinary() throws Exception {
        Resource body = new ByteArrayResource(new byte[]{1, 2, 3});
        when(animalService.getImage(1L)).thenReturn(ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(body));

        mockMvc.perform(get("/animals/1/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(new byte[]{1, 2, 3}));
    }

    @Test
    void updateImage_ShouldUsePut() throws Exception {
        Resource body = new ByteArrayResource(new byte[]{9});
        when(animalService.updateImage(eq(1L), any())).thenReturn(ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(body));

        mockMvc.perform(multipart("/animals/1/image").file(image()).with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{9}));
    }

    @Test
    void deleteImage_ShouldReturnNoContent() throws Exception {
        when(animalService.deleteImageAnimal(1L)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/animals/1/image"))
                .andExpect(status().isNoContent());
    }

    @Test
    void oldImageRoute_ShouldNotExist() throws Exception {
        mockMvc.perform(get("/animals/image/1")).andExpect(status().is4xxClientError());
    }
}
