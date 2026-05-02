package com.vetcare.petmeds.service;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.Medicine;
import com.vetcare.petmeds.repository.MedicineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicineService {
    private MedicineRepository medicineRepository;

    public Medicine getMedicineById(Long id){
        return medicineRepository.findById(id).orElse(null);
    }

    public List<Medicine> getAllMedicines(){
        return medicineRepository.findAll();
    }

    public List<Medicine> getByName(String name) {
        return medicineRepository.findByNameContainingIgnoreCase(name);
    }

    public Medicine createMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public ResponseDTO createAllsMedicine(List<Medicine> medicine) {
        for (Medicine m : medicine) {
            medicineRepository.save(m);
        }
        return new ResponseDTO(medicine.size() +" medicamentos foram criados com sucesso");
    }

    public Medicine updateMedicine(Long id, Medicine medicine) {
        Medicine existing = getMedicineById(id);

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