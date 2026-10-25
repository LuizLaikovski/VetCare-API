package com.vetcare.petmeds.modules.animal.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.repository.AnimalRepository;
import com.vetcare.petmeds.modules.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.medicine.repository.MedicineRepository;
import com.vetcare.petmeds.shared.ResponseDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AnimalService {
    private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private AnimalRepository animalRepository;
    private MedicineRepository medicineRepository;

    public Page<AnimalEntity> getAll(Pageable pageable) {
        Page<AnimalEntity> page = animalRepository.findAll(pageable);
        logger.info("Listagem de animais obtida com sucesso");
        return page;
    }

    public AnimalEntity getById(Long id) {
        AnimalEntity animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
        logger.info("Animal encontrado com sucesso para ID: {}", id);
        return animal;
    }

    public Page<AnimalEntity> getByName(String name, Pageable pageable) {
        Page<AnimalEntity> page = animalRepository.findByNameContainingIgnoreCase(name, pageable);
        logger.info("Busca de animais por nome obtida com sucesso");
        return page;
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

        logger.info("Medicamento ID {} associado com sucesso ao animal ID {}", idMedicine, idAnimal);
        return new ResponseDTO(
                new Date(),
                "Medicamento salvo com sucesso",
                "Medicamento salvo com sucesso"
        );
    }

    public AnimalEntity createNewAnimal(AnimalEntity animal) {
        AnimalEntity saved = animalRepository.save(animal);
        logger.info("Animal criado com sucesso: {}", saved.getName());
        return saved;
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

        AnimalEntity saved = animalRepository.save(existing);
        logger.info("Animal atualizado com sucesso ID: {}", id);
        return saved;
    }

    public ResponseEntity<ResponseDTO> deleteById(Long id) {
        animalRepository.deleteById(id);
        logger.info("Animal deletado com sucesso ID: {}", id);
        ResponseDTO responseDTO = new ResponseDTO(new Date(), "Animal deletado com sucesso", "Animal deletado com sucesso");
        return new ResponseEntity<>(responseDTO, HttpStatus.NO_CONTENT);
    }

    public ResponseLoginDTO createAnimals(List<AnimalEntity> animals) {
        for (AnimalEntity animal : animals) {
            createNewAnimal(animal);
        }
        logger.info("Lote de {} animais cadastrado com sucesso", animals.size());
        return new ResponseLoginDTO("Os "+ animals.toArray().length +" foram cadastrado com sucesso");
    }
}
