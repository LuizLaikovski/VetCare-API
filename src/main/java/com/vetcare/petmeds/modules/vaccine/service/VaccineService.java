package com.vetcare.petmeds.modules.vaccine.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.vaccine.entity.VaccineEntity;
import com.vetcare.petmeds.modules.vaccine.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VaccineService {
    private static final Logger logger = LoggerFactory.getLogger(VaccineService.class);
    private VaccineRepository vaccineRepository;

    public Page<VaccineEntity> getAll(Pageable pageable) {
        Page<VaccineEntity> page = vaccineRepository.findAll(pageable);
        logger.info("Listagem de vacinas obtida com sucesso");
        return page;
    }

    public VaccineEntity getById(Long id) {
        VaccineEntity vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacina não encontrada"));
        logger.info("Vacina buscada com sucesso ID: {}", id);
        return vaccine;
    }

    public Page<VaccineEntity> getByName(String name, Pageable pageable) {
        Page<VaccineEntity> page = vaccineRepository.findByVaccineNameContainingIgnoreCase(name, pageable);
        logger.info("Busca de vacinas por nome obtida com sucesso");
        return page;
    }

    public VaccineEntity createVaccine(VaccineEntity vaccineEntity) {
        VaccineEntity saved = vaccineRepository.save(vaccineEntity);
        logger.info("Vacina criada com sucesso: {}", saved.getVaccineName());
        return saved;
    }

    public VaccineEntity updateVaccine(VaccineEntity newVaccineEntity) {
        VaccineEntity isExisting = vaccineRepository.findById(newVaccineEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado uma vacina com o Id informado"));

        if (newVaccineEntity.getVaccineName() != null) { isExisting.setVaccineName(newVaccineEntity.getVaccineName()); }
        if (newVaccineEntity.getDescription() != null) { isExisting.setDescription(newVaccineEntity.getDescription()); }
        if (newVaccineEntity.getNumOfDoses() != null) { isExisting.setNumOfDoses(newVaccineEntity.getNumOfDoses()); }
        if (newVaccineEntity.getDayInterval() != null) { isExisting.setDayInterval(newVaccineEntity.getDayInterval()); }
        if (newVaccineEntity.getSpecie() != null) { isExisting.setSpecie(newVaccineEntity.getSpecie()); }

        VaccineEntity saved = vaccineRepository.save(isExisting);
        logger.info("Vacina atualizada com sucesso ID: {}", saved.getId());
        return saved;
    }

    public void delete(Long id) {
        if (!vaccineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vacina não encontrada.");
        }
        vaccineRepository.deleteById(id);
        logger.info("Vacina deletada com sucesso ID: {}", id);
    }
}
