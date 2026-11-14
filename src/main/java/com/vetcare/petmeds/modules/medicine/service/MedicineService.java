package com.vetcare.petmeds.modules.medicine.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.medicine.dto.MedicineResponseDTO;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.medicine.repository.MedicineRepository;
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
public class MedicineService {
    private static final Logger logger = LoggerFactory.getLogger(MedicineService.class);
    private MedicineRepository medicineRepository;

    public MedicineResponseDTO getMedicineById(Long id){
        return new MedicineResponseDTO(medicineRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Medicamento não encontrado.")
        ));
    }

    public MedicineEntity getMedicineByIdEntity(Long id){
        return medicineRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Medicamento não encontrado.")
        );
    }

    public Page<MedicineResponseDTO> getAllMedicines(Pageable pageable){
        return medicineRepository.findAll(pageable).map(MedicineResponseDTO::new);
    }

    public Page<MedicineResponseDTO> getByName(String name, Pageable pageable) {
        return medicineRepository.findByNameContainingIgnoreCase(name, pageable).map(MedicineResponseDTO::new);
    }

    public MedicineResponseDTO createMedicine(MedicineEntity medicine) {
        return new MedicineResponseDTO(medicineRepository.save(medicine));
    }

    public MedicineResponseDTO updateMedicine(Long id, MedicineEntity medicine) {
        MedicineEntity existing = getMedicineByIdEntity(id);

        existing.setName(medicine.getName());
        existing.setManufacturer(medicine.getManufacturer());
        existing.setType(medicine.getType());
        existing.setIndicatedSpecies(medicine.getIndicatedSpecies());
        existing.setDosage(medicine.getDosage());
        existing.setAnimal(medicine.getAnimal());

        return new MedicineResponseDTO(medicineRepository.save(existing));
    }

    public ResponseDTO deleteMedicine(Long id) {
        if(getMedicineById(id) != null) {
            medicineRepository.deleteById(id);
        }
        return new ResponseDTO(
                new Date(),
                "Deletado",
                "Medicamento deleteado com sucesso"
        );
    }
}