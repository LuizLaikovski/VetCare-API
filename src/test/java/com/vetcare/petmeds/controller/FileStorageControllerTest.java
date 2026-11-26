package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.archives.fileTest.controller.FileStorageController;
import com.vetcare.petmeds.archives.fileTest.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FileStorageController.class,
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class FileStorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileStorageService fileStorageService;

    @Test
    void upload_ShouldReturnFileMetadata() throws Exception {
        when(fileStorageService.storeFile(any())).thenReturn("doc.pdf");

        mockMvc.perform(multipart("/files")
                        .file(new MockMultipartFile("file", "doc.pdf", "application/pdf", new byte[]{1, 2})))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("doc.pdf"))
                .andExpect(jsonPath("$.fileType").value("application/pdf"))
                .andExpect(jsonPath("$.size").value(2));
    }

    @Test
    void oldUploadRoute_ShouldNotExist() throws Exception {
        mockMvc.perform(multipart("/files-test/upload")
                        .file(new MockMultipartFile("file", "doc.pdf", "application/pdf", new byte[]{1})))
                .andExpect(status().is4xxClientError());
    }
}
