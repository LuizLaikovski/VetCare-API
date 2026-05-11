package com.vetcare.petmeds.model.medicine;

import com.vetcare.petmeds.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicineService {
    private MedicineRepository medicineRepository;

    public MedicineEntity getMedicineById(Long id){
        return medicineRepository.findById(id).orElse(null);
    }

    public List<MedicineEntity> getAllMedicines(){
        return medicineRepository.findAll();
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