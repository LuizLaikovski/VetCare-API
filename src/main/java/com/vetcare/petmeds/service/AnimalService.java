package com.vetcare.petmeds.service;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.Animal;
import com.vetcare.petmeds.repository.AnimalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnimalService {
    private AnimalRepository animalRepository;

    public List<Animal> getAll() {
        return animalRepository.findAll();
    }

    public Animal getById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    public Optional<Animal> getByName(String name) {
        return animalRepository.findByName(name);
    }

    public Animal createNewAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(Animal animal) {

        Animal existing = getById(animal.getId());

        if (animal.getName() != null) {
            existing.setName(animal.getName());
        }

        if (animal.getSpecie() != null) {
            existing.setSpecie(animal.getSpecie());
        }

        if (animal.getRace() != null) {
            existing.setRace(animal.getRace());
        }

        if (animal.getAge() != null) {
            existing.setAge(animal.getAge());
        }

        if (animal.getWeight() != null) {
            existing.setWeight(animal.getWeight());
        }

        return animalRepository.save(existing);
    }

    public ResponseDTO deleteById(Long id) {
        animalRepository.deleteById(id);
        return new ResponseDTO("Animal do id "+ id +" deletado com sucesso");
    }
}
