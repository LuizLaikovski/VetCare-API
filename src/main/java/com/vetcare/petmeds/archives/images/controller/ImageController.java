package com.vetcare.petmeds.archives.images.controller;

import com.vetcare.petmeds.archives.images.dto.ImageRequestDTO;
import com.vetcare.petmeds.archives.images.dto.ImageResponseDTO;
import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
import com.vetcare.petmeds.archives.images.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@Tag(name = "Imagem", description = "Gerenciamento de rotas para o imagens na aplicação")
public class ImageController implements ImageControllerInterface {

    @Autowired
    private ImageService imageService;

    @PostMapping
    @Override
    public ImageResponseDTO uploadImage(
            @RequestParam("data") MultipartFile data
    ) {
        return imageService.uploadImageToDTO(data);
    }

    @GetMapping("/{id}")
    @Override
    public ImageResponseDTO findByIdToDTO(@PathVariable Long id) {
        return imageService.findImageByIdToDTO(id);
    }


    @Override
    public ResponseEntity<Resource> findByIdToImage(@PathVariable Long id) {
        return imageService.findByIdToImage(id);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity deleteImageById(@PathVariable("id") Long id) {
        imageService.deletedImage(id);

        return ResponseEntity.noContent().build();
    }

}
