package com.vetcare.petmeds.modules.images.controller;

import com.vetcare.petmeds.modules.images.dto.ImageRequestDTO;
import com.vetcare.petmeds.modules.images.dto.ImageResponseDTO;
import com.vetcare.petmeds.modules.images.dto.ImageUserResponseDTO;
import com.vetcare.petmeds.modules.images.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
