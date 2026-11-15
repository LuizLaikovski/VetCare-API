package com.vetcare.petmeds.archives.images.controller;

import com.vetcare.petmeds.archives.images.dto.ImageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

public interface ImageControllerInterface {
    @PostMapping
    @Operation(summary = "Cadastro de Imagem", description = "Cadastra uma nova imagem")
    ImageResponseDTO uploadImage(
            @RequestParam("data") MultipartFile data
    );

    @GetMapping("/{id}")
    @Operation(summary = "Busca Imagem através do seu ID", description = "Busca uma Imagem através do seu ID passado pela URI")
    ImageResponseDTO findByIdToDTO(@PathVariable Long id);

    @GetMapping("/file/{id}")
    @Operation(
            summary = "Buscar a imagem por ID",
            description = "Busca o arquivo pelo ID e devolve ele"
    )
    ResponseEntity<Resource> findByIdToImage(@PathVariable Long id);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta Imagem através do seu ID", description = "Deleta uma Imagem através do seu ID passado pela URI")
    ResponseEntity deleteImageById(@PathVariable("id") Long id);
}
