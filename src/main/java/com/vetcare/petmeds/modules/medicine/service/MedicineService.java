package com.vetcare.petmeds.modules.medicine.service;

import com.vetcare.petmeds.modules.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.medicine.repository.MedicineRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicineService {
    private static final Logger logger = LoggerFactory.getLogger(MedicineService.class);
    private MedicineRepository medicineRepository;

    public MedicineEntity getMedicineById(Long id){
        MedicineEntity medicine = medicineRepository.findById(id).orElse(null);
        logger.info("Medicamento buscado com sucesso ID: {}", id);
        return medicine;
    }

    public Page<MedicineEntity> getAllMedicines(Pageable pageable){
        Page<MedicineEntity> page = medicineRepository.findAll(pageable);
        logger.info("Listagem de medicamentos obtida com sucesso");
        return page;
    }

    public Page<MedicineEntity> getByName(String name, Pageable pageable) {
        Page<MedicineEntity> page = medicineRepository.findByNameContainingIgnoreCase(name, pageable);
        logger.info("Busca de medicamentos por nome obtida com sucesso");
        return page;
    }

    public MedicineEntity createMedicine(MedicineEntity medicine) {
        MedicineEntity saved = medicineRepository.save(medicine);
        logger.info("Medicamento criado com sucesso: {}", saved.getName());
        return saved;
    }

    public ResponseLoginDTO createAllsMedicine(List<MedicineEntity> medicine) {
        for (MedicineEntity m : medicine) {
            medicineRepository.save(m);
        }
        logger.info("Lote de {} medicamentos criado com sucesso", medicine.size());
        return new ResponseLoginDTO(medicine.size() +" medicamentos foram criados com sucesso");
    }

    public MedicineEntity updateMedicine(Long id, MedicineEntity medicine) {
        MedicineEntity existing = getMedicineById(id);

        if (medicine.getName() != null) { existing.setName(medicine.getName()); }
        if (medicine.getType() != null) { existing.setType(medicine.getType()); }
        if (medicine.getDosage() != null) { existing.setDosage(medicine.getDosage()); }
        if (medicine.getManufacturer() != null)  { existing.setManufacturer(medicine.getManufacturer()); }
        if (medicine.getIndicatedSpecies() != null) { existing.setIndicatedSpecies(medicine.getIndicatedSpecies()); }

        MedicineEntity saved = medicineRepository.save(existing);
        logger.info("Medicamento atualizado com sucesso ID: {}", id);
        return saved;
    }

    public ResponseLoginDTO deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
        logger.info("Medicamento deletado com sucesso ID: {}", id);
        return new ResponseLoginDTO("Medicine deleted successfully");
    }
}