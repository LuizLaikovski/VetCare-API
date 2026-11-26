package com.vetcare.petmeds.archives.fileTest.controller;

import com.vetcare.petmeds.archives.fileTest.doc.FileStorageDoc;
import com.vetcare.petmeds.archives.fileTest.dto.UploadFileResponseDTO;
import com.vetcare.petmeds.archives.fileTest.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileStorageController implements FileStorageDoc {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    @Override
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
        var fileName = fileStorageService.storeFile(file);
        logger.debug("Nome do arquivo: " + fileName);
        var fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download").path(fileName).toUriString();

        logger.debug("URI de download do arquivo: " + fileDownloadURI);
        return new UploadFileResponseDTO(
                fileName,
                fileDownloadURI,
                file.getContentType(),
                file.getSize()
        );
    }

    @Override
    public List<UploadFileResponseDTO> uploadMultipleFile(MultipartFile[] file) {
        return List.of();
    }

    @Override
    public ResponseEntity<ResponseEntity> downloadFile(String filename, HttpServletRequest request) {
        return null;
    }
}
