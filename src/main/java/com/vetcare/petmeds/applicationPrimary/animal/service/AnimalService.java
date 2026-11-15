package com.vetcare.petmeds.applicationPrimary.animal.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.repository.AnimalRepository;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
import com.vetcare.petmeds.shared.ResponseDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class AnimalService {
    private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private AnimalRepository animalRepository;
    private MedicineService medicineService;

    public Page<AnimalEntity> getAll(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }

    public AnimalEntity getById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
    }

    public Page<AnimalEntity> getByName(String name, Pageable pageable) {
        return animalRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public ResponseDTO setMedicine(Long idAnimal, Long idMedicine) {
        AnimalEntity animalExist = getById(idAnimal);

        animalExist.getMedicine().add(medicineService.getMedicineByIdEntity(idMedicine));

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

        existing.update(
                animal.getWeight(),
                animal.getAge(),
                animal.getRace(),
                animal.getOwner()
        );

        return animalRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Animal não encontrado");
        }
        animalRepository.deleteById(id);
    }
}
