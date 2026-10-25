package com.vetcare.petmeds.modules.vaccinationRecord.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.vaccinationRecord.dto.VaccinationRecordCreateDTO;
import com.vetcare.petmeds.modules.vaccinationRecord.dto.VaccinationRecordResponseDTO;
import com.vetcare.petmeds.modules.vaccinationRecord.entity.ApplicationStatus;
import com.vetcare.petmeds.modules.vaccinationRecord.entity.VaccinationRecordEntity;
import com.vetcare.petmeds.modules.vaccinationRecord.repository.VaccinationRecordRepository;
import com.vetcare.petmeds.modules.animal.dto.AnimalResponseDTO;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.repository.AnimalRepository;
import com.vetcare.petmeds.modules.user.dto.UserSimpleDTO;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import com.vetcare.petmeds.modules.user.repository.UserRepository;
import com.vetcare.petmeds.modules.vaccine.dto.VaccineResponseDTO;
import com.vetcare.petmeds.modules.vaccine.entity.VaccineEntity;
import com.vetcare.petmeds.modules.vaccine.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class VaccinationRecordService {

    private static final Logger logger = LoggerFactory.getLogger(VaccinationRecordService.class);
    private VaccinationRecordRepository vaccinationRecordRepository;
    private AnimalRepository animalRepository;
    private UserRepository userRepository;
    private VaccineRepository vaccineRepository;

    public VaccinationRecordResponseDTO mapToDTO(VaccinationRecordEntity entity) {
        AnimalEntity animal = entity.getAnimal();
        VaccineEntity vaccine = entity.getVaccine();
        UserEntity medic = entity.getMedic();

        AnimalResponseDTO animalDTO = animal != null ? AnimalResponseDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .specie(animal.getSpecie())
                .race(animal.getRace())
                .gender(animal.getGender())
                .build() : null;

        VaccineResponseDTO vaccineDTO = vaccine != null ? VaccineResponseDTO.builder()
                .id(vaccine.getId())
                .vaccineName(vaccine.getVaccineName())
                .description(vaccine.getDescription())
                .numOfDoses(vaccine.getNumOfDoses())
                .dayInterval(vaccine.getDayInterval())
                .specie(vaccine.getSpecie())
                .build() : null;

        UserSimpleDTO medicDTO = medic != null ? UserSimpleDTO.builder()
                .id(medic.getId())
                .name(medic.getName())
                .email(medic.getEmail())
                .build() : null;

        return VaccinationRecordResponseDTO.builder()
                .id(entity.getId())
                .animal(animalDTO)
                .vaccine(vaccineDTO)
                .medic(medicDTO)
                .applicationDate(entity.getApplicationDate())
                .numOfDoses(entity.getNumOfDoses())
                .observation(entity.getObservation())
                .applicationStatus(entity.getApplicationStatus() != null ? entity.getApplicationStatus().name() : null)
                .build();
    }

    public VaccinationRecordResponseDTO createVaccinationRecord(VaccinationRecordCreateDTO vaccinationRecordCreateDTO) {
        AnimalEntity animal = animalRepository.findById(vaccinationRecordCreateDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));

        UserEntity medic = userRepository.findById(vaccinationRecordCreateDTO.getMedicId())
                .orElseThrow(() -> new ResourceNotFoundException("Medico não encontrado"));

        VaccineEntity vaccine = vaccineRepository.findById(vaccinationRecordCreateDTO.getVaccineId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacina não encontrada"));

        VaccinationRecordEntity newVaccinationRecord = new VaccinationRecordEntity();
        newVaccinationRecord.setAnimal(animal);
        newVaccinationRecord.setMedic(medic);
        newVaccinationRecord.setVaccine(vaccine);
        newVaccinationRecord.setApplicationDate(vaccinationRecordCreateDTO.getApplicationDate() != null ? vaccinationRecordCreateDTO.getApplicationDate() : new Date());
        newVaccinationRecord.setNumOfDoses(vaccinationRecordCreateDTO.getNumOfDoses() != null ? vaccinationRecordCreateDTO.getNumOfDoses() : vaccine.getNumOfDoses());
        newVaccinationRecord.setObservation(vaccinationRecordCreateDTO.getObservation());

        if (vaccinationRecordCreateDTO.getApplicationStatus() != null) {
            newVaccinationRecord.setApplicationStatus(ApplicationStatus.valueOf(vaccinationRecordCreateDTO.getApplicationStatus().toUpperCase()));
        } else {
            newVaccinationRecord.setApplicationStatus(ApplicationStatus.APPLIED);
        }

        vaccinationRecordRepository.save(newVaccinationRecord);
        VaccinationRecordResponseDTO response = mapToDTO(newVaccinationRecord);
        logger.info("Aplicação de vacina registrada com sucesso!");
        return response;
    }

    public VaccinationRecordResponseDTO findById(Long id) {
        VaccinationRecordEntity entity = vaccinationRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de vacinação não encontrado"));
        VaccinationRecordResponseDTO response = mapToDTO(entity);
        logger.info("Registro de vacinação buscado com sucesso ID: {}", id);
        return response;
    }

    public Page<VaccinationRecordResponseDTO> findAll(Pageable pageable) {
        Page<VaccinationRecordResponseDTO> page = vaccinationRecordRepository.findAll(pageable).map(this::mapToDTO);
        logger.info("Listagem de registros de vacinação obtida com sucesso");
        return page;
    }

    public List<VaccinationRecordResponseDTO> findAllList() {
        List<VaccinationRecordResponseDTO> list = vaccinationRecordRepository.findAll().stream().map(this::mapToDTO).toList();
        logger.info("Listagem completa de registros de vacinação obtida com sucesso");
        return list;
    }

    public Page<VaccinationRecordResponseDTO> findByAnimalId(Long animalId, Pageable pageable) {
        Page<VaccinationRecordResponseDTO> page = vaccinationRecordRepository.findByAnimalId(animalId, pageable).map(this::mapToDTO);
        logger.info("Busca de registros de vacinação por animal ID obtida com sucesso");
        return page;
    }

    public Page<VaccinationRecordResponseDTO> findByMedicId(Long medicId, Pageable pageable) {
        Page<VaccinationRecordResponseDTO> page = vaccinationRecordRepository.findByMedicId(medicId, pageable).map(this::mapToDTO);
        logger.info("Busca de registros de vacinação por médico ID obtida com sucesso");
        return page;
    }

    public Page<VaccinationRecordResponseDTO> findByVaccineId(Long vaccineId, Pageable pageable) {
        Page<VaccinationRecordResponseDTO> page = vaccinationRecordRepository.findByVaccineId(vaccineId, pageable).map(this::mapToDTO);
        logger.info("Busca de registros de vacinação por vacina ID obtida com sucesso");
        return page;
    }

    public Page<VaccinationRecordResponseDTO> findByApplicationDate(Date applicationDate, Pageable pageable) {
        LocalDate localDate = applicationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date startOfDay = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        Page<VaccinationRecordResponseDTO> page = vaccinationRecordRepository.findByApplicationDateBetween(startOfDay, endOfDay, pageable).map(this::mapToDTO);
        logger.info("Busca de registros de vacinação por data obtida com sucesso");
        return page;
    }

    public Page<VaccinationRecordResponseDTO> findByDateRange(Date startDate, Date endDate, Pageable pageable) {
        Page<VaccinationRecordResponseDTO> page = vaccinationRecordRepository.findByApplicationDateBetween(startDate, endDate, pageable).map(this::mapToDTO);
        logger.info("Busca de registros de vacinação por intervalo de datas obtida com sucesso");
        return page;
    }

    public Page<VaccinationRecordResponseDTO> findByApplicationStatus(String status, Pageable pageable) {
        ApplicationStatus appStatus = ApplicationStatus.valueOf(status.toUpperCase());
        Page<VaccinationRecordResponseDTO> page = vaccinationRecordRepository.findByApplicationStatus(appStatus, pageable).map(this::mapToDTO);
        logger.info("Busca de registros de vacinação por status obtida com sucesso");
        return page;
    }

}
