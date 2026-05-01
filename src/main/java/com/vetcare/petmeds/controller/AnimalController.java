package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.Animal;
import com.vetcare.petmeds.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animal")
@AllArgsConstructor
public class AnimalController {
    private AnimalService animalService;

    @PostMapping("/create")
    public ResponseDTO createAnimal(@RequestBody Animal animal) {
        animalService.createNewAnimal(animal);
        return new ResponseDTO("Animal cadastrado com sucesso");
    }

    @PostMapping("/createAll")
    public ResponseDTO createNewAnimals(@RequestBody List<Animal> animals) {
        animalService.createAnimals(animals);
        return  new ResponseDTO("Animal cadastrado com sucesso");
    }

    @GetMapping("/id/{id}")
    public Animal getById(@PathVariable Long id) {
        return animalService.getById(id);
    }

    @GetMapping("/all")
    public List<Animal> getAllAnimals() {
        return animalService.getAll();
    }

    @GetMapping("/{name}")
    public Optional<Animal> getByName(@PathVariable String name) {
        return animalService.getByName(name);
    }

    @PutMapping("/edit/{id}")
    public ResponseDTO updateAnimal(@PathVariable Long id, @RequestBody Animal animal) {
        animalService.updateAnimal(id, animal);
        return new ResponseDTO("Animal atualizado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteAnimal(@PathVariable Long id) {
        animalService.deleteById(id);
        return new ResponseDTO("Animal deletado com sucesso");
    }
}