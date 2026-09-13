package com.vetcare.petmeds.modules.animal.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.repository.AnimalRepository;
import com.vetcare.petmeds.modules.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.medicine.repository.MedicineRepository;
import com.vetcare.petmeds.shared.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnimalService {
    private AnimalRepository animalRepository;
    private MedicineRepository medicineRepository;

    public Page<AnimalEntity> getAll(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }

    public AnimalEntity getById(Long id) {
        return animalRepository.findById(id)
                //.orElseThrow(() -> new RuntimeException("Animal não encontrado"));
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
    }

    public Optional<AnimalEntity> getByName(String name) {
        return Optional.of(animalRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado")));
    }

    public ResponseDTO setMedicine(Long idAnimal, Long idMedicine) {
        MedicineEntity medicineExist = medicineRepository.getById(idMedicine);
        AnimalEntity animalExist = animalRepository.getById(idAnimal);

        if (medicineExist == null) {
            throw new ResourceNotFoundException("Medicamento não encontrado");
        }

        if (animalExist == null) {
            throw new ResourceNotFoundException("Animal não encontrado");
        }

        animalExist.getMedicine().add(medicineExist);

        medicineExist.setAnimal(animalExist);

        animalRepository.save(animalExist);

        return new ResponseDTO(
                new Date(),
                "Medicamento salvo com sucesso",
                "Medicamento salvo com sucesso"
        );
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

    public ResponseEntity<ResponseDTO> deleteById(Long id) {
        animalRepository.deleteById(id);
        ResponseDTO responseDTO = new ResponseDTO(new Date(), "Animal deletado com sucesso", "Animal deletado com sucesso");
        return new ResponseEntity<>(responseDTO, HttpStatus.NO_CONTENT);
    }

    public ResponseLoginDTO createAnimals(List<AnimalEntity> animals) {
        for (AnimalEntity animal : animals) {
            createNewAnimal(animal);
        }

        return new ResponseLoginDTO("Os "+ animals.toArray().length +" foram cadastrado com sucesso");
    }
}
