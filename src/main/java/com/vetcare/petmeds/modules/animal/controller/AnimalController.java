package com.vetcare.petmeds.modules.animal.controller;

import com.vetcare.petmeds.shared.ResponseDTO;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animal")
@AllArgsConstructor
@Tag(name = "Animal", description = "Gerenciamento de rotas para os animais")
public class AnimalController {
    private AnimalService animalService;

    @PostMapping("/create")
    @Operation(summary = "Cadastro de Animal", description = "Essa rota é responsável por realizar o cadastro de um novo animal no sistema")
    public ResponseEntity<ResponseDTO> createAnimal(@RequestBody AnimalEntity animal) {
        animalService.createNewAnimal(animal);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Animal cadastrado com sucesso",
                "Animal cadastrado com sucesso"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/createAll")
    @Operation(summary = "Cadastro de múltiplos animais", description = "Cadastra uma lista de novos animais")
    public ResponseEntity<ResponseDTO> createNewAnimals(@RequestBody List<AnimalEntity> animals) {
        animalService.createAnimals(animals);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Animais cadastrados com sucesso",
                "Animais cadastrados com sucesso"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/addMedicine")
    @Operation(summary = "Adicionar medicamento a um animal", description = "Associa um medicamento a um animal")
    public ResponseEntity<ResponseDTO> addMedicine(@RequestParam Long idAnimal, @RequestParam Long idMedicine) {
        return ResponseEntity.ok(animalService.setMedicine(idAnimal, idMedicine));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar animal por ID", description = "Busca os detalhes de um animal pelo seu ID")
    public ResponseEntity<AnimalEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os animais", description = "Retorna uma lista paginada de animais")
    public ResponseEntity<Page<AnimalEntity>> getAllAnimals(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(animalService.getAll(pageable));
    }

    @GetMapping("/{name}")
    @Operation(summary = "Buscar animal por nome", description = "Busca um animal pelo seu nome")
    public ResponseEntity<AnimalEntity> getByName(@PathVariable String name) {
        return animalService.getByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Atualizar animal", description = "Atualiza os dados de um animal existente")
    public ResponseEntity<ResponseDTO> updateAnimal(@PathVariable Long id, @RequestBody AnimalEntity animal) {
        animalService.updateAnimal(id, animal);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Animal atualizado com sucesso",
                "Animal atualizado com sucesso"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover animal", description = "Remove um animal do sistema")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}