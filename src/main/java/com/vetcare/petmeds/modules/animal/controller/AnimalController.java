package com.vetcare.petmeds.modules.animal.controller;

import com.vetcare.petmeds.modules.user.dto.ResponseDTO;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animal")
@AllArgsConstructor
public class AnimalController {
    private AnimalService animalService;

    @PostMapping("/create")
    public ResponseDTO createAnimal(@RequestBody AnimalEntity animal) {
        animalService.createNewAnimal(animal);
        return new ResponseDTO("Animal cadastrado com sucesso");
    }

    @PostMapping("/createAll")
    public ResponseDTO createNewAnimals(@RequestBody List<AnimalEntity> animals) {
        animalService.createAnimals(animals);
        return  new ResponseDTO("Animal cadastrado com sucesso");
    }

    @PostMapping("/addMedicine")
    public ResponseDTO addMedicine(@RequestBody Long idAnimal, @RequestBody Long idMedicine) {
        return animalService.setMedicine(idAnimal, idMedicine);
    }

    @GetMapping("/id/{id}")
    public AnimalEntity getById(@PathVariable Long id) {
        return animalService.getById(id);
    }

    @GetMapping("/all")
    public Page<AnimalEntity> getAllAnimals(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return animalService.getAll(pageable);
    }

    @GetMapping("/{name}")
    public Optional<AnimalEntity> getByName(@PathVariable String name) {
        return animalService.getByName(name);
    }

    @PutMapping("/edit/{id}")
    public ResponseDTO updateAnimal(@PathVariable Long id, @RequestBody AnimalEntity animal) {
        animalService.updateAnimal(id, animal);
        return new ResponseDTO("Animal atualizado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteAnimal(@PathVariable Long id) {
        animalService.deleteById(id);
        return new ResponseDTO("Animal deletado com sucesso");
    }
}