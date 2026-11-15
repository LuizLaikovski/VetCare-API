package com.vetcare.petmeds.archives.images.controller;

import com.vetcare.petmeds.archives.images.dto.ImageRequestDTO;
import com.vetcare.petmeds.archives.images.dto.ImageResponseDTO;
import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
import com.vetcare.petmeds.archives.images.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ImageResponseDTO uploadImage(
            @RequestParam("data") MultipartFile data
    ) {
        return imageService.uploadImageToDTO(data);
    }

    @GetMapping("/{id}")
    public ImageResponseDTO findByIdToDTO(@PathVariable Long id) {
        return imageService.findImageByIdToDTO(id);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> findByIdToImage(@PathVariable Long id) {
        return imageService.findByIdToImage(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteImageById(@PathVariable("id") Long id) {
        imageService.deletedImage(id);

        return ResponseEntity.noContent().build();
    }

}
