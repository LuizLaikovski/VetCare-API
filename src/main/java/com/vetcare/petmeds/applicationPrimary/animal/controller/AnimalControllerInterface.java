package com.vetcare.petmeds.applicationPrimary.animal.controller;

import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalDescriptionDTO;
import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalSimpleDescriptionDTO;
import com.vetcare.petmeds.shared.ResponseDTO;
import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

public interface AnimalControllerInterface {

    @PostMapping
    @Operation(summary = "Cadastro de Animal", description = "Essa rota é responsável por realizar o cadastro de um novo animal no sistema")
    ResponseEntity<ResponseDTO> createAnimal(@RequestBody AnimalEntity animal);

    @PostMapping("/addMedicine")
    @Operation(summary = "Adicionar medicamento a um animal", description = "Associa um medicamento a um animal")
    ResponseEntity<ResponseDTO> addMedicine(@RequestParam Long idAnimal, @RequestParam Long idMedicine);

    @PostMapping("/image/{id}")
    @Operation(summary = "Insere uma imagem ao cadastro do animal", description = "Insere uma imagem a um animal")
    ResponseEntity<AnimalSimpleDescriptionDTO> setImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    );

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar animal por ID", description = "Busca os detalhes de um animal pelo seu ID")
    ResponseEntity<AnimalEntity> getById(@PathVariable Long id);

    @GetMapping("/all")
    @Operation(summary = "Listar todos os animais", description = "Retorna uma lista paginada de animais")
    ResponseEntity<Page<AnimalDescriptionDTO>> getAllAnimals(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar animal por nome", description = "Busca animais pelo nome com paginação")
    ResponseEntity<Page<AnimalEntity>> getByName(
            @PathVariable String name,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @PutMapping("/edit/{id}")
    @Operation(summary = "Atualizar animal", description = "Atualiza os dados de um animal existente")
    ResponseEntity<ResponseDTO> updateAnimal(@PathVariable Long id, @RequestBody AnimalEntity animal);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover animal", description = "Remove um animal do sistema")
    ResponseEntity<Void> deleteAnimal(@PathVariable Long id);
}
