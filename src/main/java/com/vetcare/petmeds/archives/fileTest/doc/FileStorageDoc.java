package com.vetcare.petmeds.archives.fileTest.doc;

import com.vetcare.petmeds.archives.fileTest.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Enpoint de arquivos")
public interface FileStorageDoc {

    UploadFileResponseDTO uploadFile(MultipartFile file);
    List<UploadFileResponseDTO> uploadMultipleFile(MultipartFile[] file);
    ResponseEntity<ResponseEntity> downloadFile(String filename, HttpServletRequest request);

}
