package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.Animal;
import com.vetcare.petmeds.service.AnimalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/animal")
public class AnimalController {
    private AnimalService animalService;

    @PostMapping
    public ResponseDTO createAnimal(@RequestBody Animal animal) {
        animalService.createNewAnimal(animal);
        return new ResponseDTO("Animal cadastrado com sucesso");
    }

    @GetMapping("/{id}")
    public Animal getById(@PathVariable Long id) {
        return animalService.getById(id);
    }

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.getAll();
    }

    @GetMapping("/{name}")
    public Optional<Animal> getByName(@PathVariable String name) {
        return animalService.getByName(name);
    }

    @PutMapping
    public ResponseDTO updateAnimal(@RequestBody Animal animal) {
        animalService.updateAnimal(animal);
        return new ResponseDTO("Animal atualizado com sucesso");
    }

    @DeleteMapping
    public ResponseDTO deleteAnimal(@RequestBody Animal animal) {
        animalService.deleteById(animal.getId());
        return new ResponseDTO("Animal deletado com sucesso");
    }
}