package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.applicationPrimary.user.controller.UserController;
import com.vetcare.petmeds.applicationPrimary.user.service.UserService;
import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Rotas de imagem do usuário: /users/{id}/image. */
@WebMvcTest(controllers = UserController.class,
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class UserImageRoutesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private MockMultipartFile image() {
        return new MockMultipartFile("image", "perfil.png", MediaType.IMAGE_PNG_VALUE, new byte[]{1, 2, 3});
    }

    private ImageUserResponseDTO dto() {
        ImageUserResponseDTO dto = new ImageUserResponseDTO();
        dto.setNameUser("John");
        dto.setName("perfil.png");
        return dto;
    }

    @Test
    void uploadImage_ShouldReturnCreated() throws Exception {
        when(userService.uploadImageToUser(eq(1L), any())).thenReturn(dto());

        mockMvc.perform(multipart("/users/1/image").file(image()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameUser").value("John"));
    }

    @Test
    void updateImage_ShouldUsePut() throws Exception {
        when(userService.updateImageToUser(eq(1L), any())).thenReturn(dto());

        mockMvc.perform(multipart("/users/1/image").file(image()).with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("perfil.png"));
    }

    @Test
    void getImage_ShouldReturnBinary() throws Exception {
        when(userService.getByImage(1L)).thenReturn(ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG).body(new ByteArrayResource(new byte[]{7})));

        mockMvc.perform(get("/users/1/image"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{7}));
    }

    @Test
    void deleteImage_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/users/1/image"))
                .andExpect(status().isNoContent());

        verify(userService).deletedImageToUser(1L);
    }

    @Test
    void updateUser_ShouldUsePutOnResource() throws Exception {
        when(userService.updateUser(eq(1L), any())).thenReturn(new com.vetcare.petmeds.applicationPrimary.user.dto.ResponseLoginDTO("ok"));

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("ok"));
    }

    @Test
    void oldUpdateRoute_ShouldNotExist() throws Exception {
        mockMvc.perform(put("/users/update/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().is4xxClientError());
    }
}
