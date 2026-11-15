package com.vetcare.petmeds.applicationPrimary.vaccine.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.applicationPrimary.vaccine.entity.VaccineEntity;
import com.vetcare.petmeds.applicationPrimary.vaccine.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VaccineService {
    private static final Logger logger = LoggerFactory.getLogger(VaccineService.class);
    private VaccineRepository vaccineRepository;

    public Page<VaccineEntity> getAll(Pageable pageable) {
        return vaccineRepository.findAll(pageable);
    }

    public VaccineEntity getById(Long id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacina não encontrada"));
    }

    public Page<VaccineEntity> getByName(String name, Pageable pageable) {
        return vaccineRepository.findByVaccineNameContainingIgnoreCase(name, pageable);
    }

    public VaccineEntity createVaccine(VaccineEntity vaccineEntity) {
        return vaccineRepository.save(vaccineEntity);
    }

    public VaccineEntity updateVaccine(VaccineEntity newVaccineEntity) {
        VaccineEntity isExisting = getById(newVaccineEntity.getId());

        if (newVaccineEntity.getVaccineName() != null) { isExisting.setVaccineName(newVaccineEntity.getVaccineName()); }
        if (newVaccineEntity.getDescription() != null) { isExisting.setDescription(newVaccineEntity.getDescription()); }
        if (newVaccineEntity.getNumOfDoses() != null) { isExisting.setNumOfDoses(newVaccineEntity.getNumOfDoses()); }
        if (newVaccineEntity.getDayInterval() != null) { isExisting.setDayInterval(newVaccineEntity.getDayInterval()); }
        if (newVaccineEntity.getSpecie() != null) { isExisting.setSpecie(newVaccineEntity.getSpecie()); }

        return vaccineRepository.save(isExisting);
    }

    public void delete(Long id) {
        if (!vaccineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vacina não encontrada.");
        }
        vaccineRepository.deleteById(id);
    }
}
