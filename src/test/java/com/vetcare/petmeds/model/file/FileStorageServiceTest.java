package com.vetcare.petmeds.model.file;

import com.vetcare.petmeds.archives.fileTest.service.FileStorageService;
import com.vetcare.petmeds.config.FileStorageConfig;
import com.vetcare.petmeds.exception.FileStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileStorageServiceTest {

    @TempDir
    Path tempDir;

    private FileStorageService service;

    @BeforeEach
    void setUp() {
        FileStorageConfig config = new FileStorageConfig();
        config.setUploadDir(tempDir.resolve("uploads").toString());
        service = new FileStorageService(config);
    }

    @Test
    void constructor_ShouldCreateUploadDirectory() {
        assertTrue(Files.isDirectory(tempDir.resolve("uploads")));
    }

    @Test
    void storeFile_ShouldWriteFileAndReturnName() throws Exception {
        String name = service.storeFile(new MockMultipartFile("file", "doc.txt", "text/plain", "conteudo".getBytes()));

        assertEquals("doc.txt", name);
        assertEquals("conteudo", Files.readString(tempDir.resolve("uploads").resolve("doc.txt")));
    }

    @Test
    void storeFile_ShouldReplaceExistingFile() throws Exception {
        service.storeFile(new MockMultipartFile("file", "doc.txt", "text/plain", "um".getBytes()));
        service.storeFile(new MockMultipartFile("file", "doc.txt", "text/plain", "dois".getBytes()));

        assertEquals("dois", Files.readString(tempDir.resolve("uploads").resolve("doc.txt")));
    }

    @Test
    void storeFile_ShouldRejectPathTraversal() {
        MockMultipartFile evil = new MockMultipartFile("file", "../fora.txt", "text/plain", "x".getBytes());

        assertThrows(FileStorageException.class, () -> service.storeFile(evil));
        assertFalse(Files.exists(tempDir.resolve("fora.txt")));
    }
}
