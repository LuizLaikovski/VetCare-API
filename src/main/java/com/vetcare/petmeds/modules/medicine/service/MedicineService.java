package com.vetcare.petmeds.modules.medicine.service;

import com.vetcare.petmeds.modules.user.dto.ResponseDTO;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.medicine.repository.MedicineRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicineService {
    private MedicineRepository medicineRepository;

    public MedicineEntity getMedicineById(Long id){
        return medicineRepository.findById(id).orElse(null);
    }

    public Page<MedicineEntity> getAllMedicines(Pageable pageable){
        return medicineRepository.findAll(pageable);
    }

    public List<MedicineEntity> getByName(String name) {
        return medicineRepository.findByNameContainingIgnoreCase(name);
    }

    public MedicineEntity createMedicine(MedicineEntity medicine) {
        return medicineRepository.save(medicine);
    }

    public ResponseDTO createAllsMedicine(List<MedicineEntity> medicine) {
        for (MedicineEntity m : medicine) {
            medicineRepository.save(m);
        }
        return new ResponseDTO(medicine.size() +" medicamentos foram criados com sucesso");
    }

    public MedicineEntity updateMedicine(Long id, MedicineEntity medicine) {
        MedicineEntity existing = getMedicineById(id);

        if (medicine.getName() != null) { existing.setName(medicine.getName()); }
        if (medicine.getType() != null) { existing.setType(medicine.getType()); }
        if (medicine.getDosage() != null) { existing.setDosage(medicine.getDosage()); }
        if (medicine.getManufacturer() != null)  { existing.setManufacturer(medicine.getManufacturer()); }
        if (medicine.getIndicatedSpecies() != null) { existing.setIndicatedSpecies(medicine.getIndicatedSpecies()); }

        return medicineRepository.save(existing);
    }

    public ResponseDTO deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
        return new ResponseDTO("Medicine deleted successfully");
    }
}