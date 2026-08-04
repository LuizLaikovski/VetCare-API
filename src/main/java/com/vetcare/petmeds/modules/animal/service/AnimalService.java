package com.vetcare.petmeds.modules.animal.service;

import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.repository.AnimalRepository;
import com.vetcare.petmeds.modules.user.dto.ResponseDTO;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.medicine.repository.MedicineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnimalService {
    private AnimalRepository animalRepository;
    private MedicineRepository medicineRepository;

    public List<AnimalEntity> getAll() {
        return animalRepository.findAll();
    }

    public AnimalEntity getById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    public Optional<AnimalEntity> getByName(String name) {
        return animalRepository.findByName(name);
    }

    public ResponseDTO setMedicine(Long idAnimal, Long idMedicine) {
        MedicineEntity medicineExist = medicineRepository.getById(idMedicine);
        AnimalEntity animalExist = animalRepository.getById(idAnimal);

        if (medicineExist == null) {
            return new ResponseDTO("Medicamento não encontrado");
        }

        if (animalExist == null) {
            return new ResponseDTO("Animal não encontrado");
        }

        animalExist.getMedicine().add(medicineExist);

        medicineExist.setAnimal(animalExist);

        animalRepository.save(animalExist);

        return new ResponseDTO("Medicamento salvo com sucesso");
    }

    public AnimalEntity createNewAnimal(AnimalEntity animal) {
        return animalRepository.save(animal);
    }

    public AnimalEntity updateAnimal(Long id, AnimalEntity animal)  {

        AnimalEntity existing = getById(id);

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

    public ResponseDTO createAnimals(List<AnimalEntity> animals) {
        for (AnimalEntity animal : animals) {
            createNewAnimal(animal);
        }

        return new ResponseDTO("Os "+ animals.toArray().length +" foram cadastrado com sucesso");
    }
}
