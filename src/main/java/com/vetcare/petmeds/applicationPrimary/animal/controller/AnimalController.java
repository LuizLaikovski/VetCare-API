package com.vetcare.petmeds.applicationPrimary.animal.controller;

import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalDescriptionDTO;
import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalSimpleDescriptionDTO;
import com.vetcare.petmeds.shared.ResponseDTO;
import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.service.AnimalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/animal")
@AllArgsConstructor
@Tag(name = "Animal", description = "Gerenciamento de rotas para os animais")
public class AnimalController implements AnimalControllerInterface {
    private AnimalService animalService;

    @Override
    public ResponseEntity<ResponseDTO> createAnimal(@RequestBody AnimalEntity animal) {
        animalService.createNewAnimal(animal);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Animal cadastrado com sucesso",
                "Animal cadastrado com sucesso"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDTO> addMedicine(@RequestParam Long idAnimal, @RequestParam Long idMedicine) {
        return ResponseEntity.ok(animalService.setMedicine(idAnimal, idMedicine));
    }

    @Override
    public ResponseEntity<AnimalSimpleDescriptionDTO> setImage(Long id, MultipartFile image) {
        return ResponseEntity.ok(animalService.setImage(id, image));
    }

    @Override
    public ResponseEntity<AnimalEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getById(id));
    }

    @Override
    public ResponseEntity<Page<AnimalDescriptionDTO>> getAllAnimals(Pageable pageable) {
        return ResponseEntity.ok(animalService.getAll(pageable));
    }

    @Override
    public ResponseEntity<Page<AnimalEntity>> getByName(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(animalService.getByName(name, pageable));
    }

    @Override
    public ResponseEntity<ResponseDTO> updateAnimal(@PathVariable Long id, @RequestBody AnimalEntity animal) {
        animalService.updateAnimal(id, animal);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Animal atualizado com sucesso",
                "Animal atualizado com sucesso"
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
