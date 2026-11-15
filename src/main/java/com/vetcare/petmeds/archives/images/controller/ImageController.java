package com.vetcare.petmeds.archives.images.controller;

import com.vetcare.petmeds.archives.images.dto.ImageRequestDTO;
import com.vetcare.petmeds.archives.images.dto.ImageResponseDTO;
import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
import com.vetcare.petmeds.archives.images.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestParam("name") String name,
            @RequestParam("typeContent") String typeContent,
            @RequestParam("data") MultipartFile data
    ) {
        return imageService.uploadImageToDTO(new ImageRequestDTO(name, typeContent, data));
    }

    @PostMapping("/user/{id}")
    public ImageUserResponseDTO uploadImagetoUser(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("typeContent") String typeContent,
            @RequestParam("data") MultipartFile data
    ) {
        return imageService.uploadImageToUser(id, new ImageRequestDTO(name, typeContent, data));
    }

    @GetMapping("/{id}")
    public ImageResponseDTO findByIdToDTO(@PathVariable Long id) {
        return imageService.findImageByIdToDTO(id);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> findByIdToImage(@PathVariable Long id) {
        return imageService.findByIdToImage(id);
    }

}
