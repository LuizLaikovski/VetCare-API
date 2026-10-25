package com.vetcare.petmeds.modules.history.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.repository.AnimalRepository;
import com.vetcare.petmeds.modules.history.dto.HistoryRequestDTO;
import com.vetcare.petmeds.modules.history.dto.HistoryResponseDTO;
import com.vetcare.petmeds.modules.history.entity.HistoryEntity;
import com.vetcare.petmeds.modules.history.repository.HistoryRepository;
import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.modules.initialCheckUp.repository.InitialCheckUpRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class HistoryService {
    private static final Logger logger = LoggerFactory.getLogger(HistoryService.class);
    HistoryRepository repository;
    AnimalRepository animalRepository;
    InitialCheckUpRepository initialCheckUpRepository;

    public HistoryResponseDTO mapToDTO(HistoryEntity historyEntity) {
        AnimalEntity animal = historyEntity.getAnimal();
        InitialCheckUpEntity checkUp = historyEntity.getCheckUp();

        String animalDesc = "";
        if (animal != null) {
            String ownerName = (animal.getOwner() != null) ? animal.getOwner().getName() : "Não informado";
            animalDesc = "Especie: "+ animal.getSpecie() + "\n" +
                    "Raça: "+ animal.getRace() + "\n" +
                    "Idade: "+ animal.getAge() + "\n" +
                    "Peso: "+ animal.getWeight() + "\n" +
                    "Genero: "+ animal.getGender() + "\n" +
                    "Nome do Dono: "+ ownerName + "\n";
        }

        String checkupDesc = "";
        if (checkUp != null) {
            String vetName = (checkUp.getVeterinarian() != null) ? checkUp.getVeterinarian().getName() : "Não informado";
            String hasEcto = (checkUp.getHasEctoparasites() != null && checkUp.getHasEctoparasites()) ? "Sim" : "Não";
            checkupDesc = "Data do Exame: " + checkUp.getExaminationDate() + "\n" +
                    "Veterinário Responsável: " + vetName + "\n" +
                    "Espécie: " + checkUp.getSpecies() + "\n" +
                    "Sexo: " + checkUp.getSex() + "\n" +
                    "CRT (Tempo de Preenchimento Capilar): " + checkUp.getCrt() + "\n" +
                    "FR (Frequência Respiratória): " + checkUp.getRr() + "\n" +
                    "FC (Frequência Cardíaca): " + checkUp.getHr() + "\n" +
                    "Mucosas: " + checkUp.getTpc() + "\n" +
                    "Hidratação: " + checkUp.getHydration() + "\n" +
                    "Pulso Arterial: " + checkUp.getArterialPulse() + "\n" +
                    "Mucosas (Status): " + checkUp.getMucousMembranes() + "\n" +
                    "Nível de Consciência: " + checkUp.getConsciousnessLevel() + "\n" +
                    "Estado Nutricional: " + checkUp.getNutritionalStatus() + "\n" +
                    "Comportamento: " + checkUp.getBehavior() + "\n" +
                    "Presença de Ectoparasitas: " + hasEcto + "\n" +
                    "Detalhes Ectoparasitas: " + checkUp.getEctoparasitesDetails() + "\n" +
                    "Postura e Movimentação: " + checkUp.getPostureMovement() + "\n" +
                    "Olhos: " + checkUp.getEyes() + "\n" +
                    "Ouvidos: " + checkUp.getEars() + "\n" +
                    "Cavidade Oral: " + checkUp.getOralCavity() + "\n" +
                    "Sistema Respiratório: " + checkUp.getRespiratory() + "\n" +
                    "Sistema Circulatório: " + checkUp.getCirculatory() + "\n" +
                    "Sistema Hemolinfático: " + checkUp.getHemolymphatic() + "\n" +
                    "Sistema Digestório: " + checkUp.getDigestive() + "\n" +
                    "Sistema Genital: " + checkUp.getGenital() + "\n" +
                    "Sistema Urinário: " + checkUp.getUrinary() + "\n" +
                    "Sistema Nervoso: " + checkUp.getNervous() + "\n" +
                    "Pele e Anexos: " + checkUp.getSkinAndAppendages() + "\n" +
                    "Outros: " + checkUp.getOtherStatus() + "\n" +
                    "Observações Adicionais: " + checkUp.getOtherObservations() + "\n";
        }

        return HistoryResponseDTO.builder()
                .id(historyEntity.getId())
                .idAnimal(animal != null ? animal.getId() : null)
                .nameAnimal(animal != null ? animal.getName() : null)
                .descriptionAnimal(animalDesc)
                .idCheckup(checkUp != null ? checkUp.getId() : null)
                .descriptionCheckup(checkupDesc)
                .build();
    }

    public HistoryResponseDTO create(HistoryRequestDTO requestDTO) {
        AnimalEntity animal = animalRepository.findById(requestDTO.getIdAnimal())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));
        InitialCheckUpEntity checkUp = initialCheckUpRepository.findById(requestDTO.getIdLastCheckup())
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado."));

        HistoryEntity history = new HistoryEntity();
        history.setAnimal(animal);
        history.setCheckUp(checkUp);

        HistoryResponseDTO response = mapToDTO(repository.save(history));
        logger.info("Histórico criado com sucesso para animal ID: {}", requestDTO.getIdAnimal());
        return response;
    }

    public HistoryResponseDTO update(Long id, HistoryRequestDTO requestDTO) {
        HistoryEntity history = findEntityById(id);
        AnimalEntity animal = animalRepository.findById(requestDTO.getIdAnimal())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));
        InitialCheckUpEntity checkUp = initialCheckUpRepository.findById(requestDTO.getIdLastCheckup())
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado."));

        history.setAnimal(animal);
        history.setCheckUp(checkUp);

        HistoryResponseDTO response = mapToDTO(repository.save(history));
        logger.info("Histórico atualizado com sucesso ID: {}", id);
        return response;
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Histórico não encontrado.");
        }
        repository.deleteById(id);
        logger.info("Histórico deletado com sucesso ID: {}", id);
    }

    @Transactional(readOnly = true)
    public HistoryResponseDTO findById(Long id) {
        HistoryResponseDTO response = mapToDTO(findEntityById(id));
        logger.info("Histórico buscado com sucesso ID: {}", id);
        return response;
    }

    public HistoryEntity findEntityById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Não foi encontrado o historico com o ID informado.")
        );
    }

    @Transactional(readOnly = true)
    public Page<HistoryResponseDTO> findAll(Pageable pageable) {
        Page<HistoryResponseDTO> page = repository.findAll(pageable).map(this::mapToDTO);
        logger.info("Listagem de históricos obtida com sucesso");
        return page;
    }
}
