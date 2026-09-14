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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryService {
    HistoryRepository repository;
    AnimalRepository animalRepository;
    InitialCheckUpRepository initialCheckUpRepository;

    public HistoryResponseDTO mapToDTO(HistoryEntity historyEntity) {
        return HistoryResponseDTO.builder()
                .id(historyEntity.getId())
                .idAnimal(historyEntity.getAnimal().getId())
                .nameAnimal(historyEntity.getAnimal().getName())
                .descriptionAnimal(
                        "Especie: "+ historyEntity.getAnimal().getSpecie() + "\n" +
                        "Raça: "+ historyEntity.getAnimal().getRace() + "\n" +
                        "Idade: "+ historyEntity.getAnimal().getAge() + "\n" +
                        "Peso: "+ historyEntity.getAnimal().getWeight() + "\n" +
                        "Genero: "+ historyEntity.getAnimal().getGender() + "\n" +
                        "Nome do Dono: "+ historyEntity.getAnimal().getOwner().getName() + "\n"
                )
                .idCheckup(historyEntity.getCheckUp().getId())
                .descriptionCheckup(
                        "Data do Exame: " + historyEntity.getCheckUp().getExaminationDate() + "\n" +
                        "Veterinário Responsável: " + historyEntity.getCheckUp().getVeterinarian().getName() + "\n" +
                        "Espécie: " + historyEntity.getCheckUp().getSpecies() + "\n" +
                        "Sexo: " + historyEntity.getCheckUp().getSex() + "\n" +
                        "CRT (Tempo de Preenchimento Capilar): " + historyEntity.getCheckUp().getCrt() + "\n" +
                        "FR (Frequência Respiratória): " + historyEntity.getCheckUp().getRr() + "\n" +
                        "FC (Frequência Cardíaca): " + historyEntity.getCheckUp().getHr() + "\n" +
                        "Mucosas: " + historyEntity.getCheckUp().getTpc() + "\n" +
                        "Hidratação: " + historyEntity.getCheckUp().getHydration() + "\n" +
                        "Pulso Arterial: " + historyEntity.getCheckUp().getArterialPulse() + "\n" +
                        "Mucosas (Status): " + historyEntity.getCheckUp().getMucousMembranes() + "\n" +
                        "Nível de Consciência: " + historyEntity.getCheckUp().getConsciousnessLevel() + "\n" +
                        "Estado Nutricional: " + historyEntity.getCheckUp().getNutritionalStatus() + "\n" +
                        "Comportamento: " + historyEntity.getCheckUp().getBehavior() + "\n" +
                        "Presença de Ectoparasitas: " + (historyEntity.getCheckUp().getHasEctoparasites() ? "Sim" : "Não") + "\n" +
                        "Detalhes Ectoparasitas: " + historyEntity.getCheckUp().getEctoparasitesDetails() + "\n" +
                        "Postura e Movimentação: " + historyEntity.getCheckUp().getPostureMovement() + "\n" +
                        "Olhos: " + historyEntity.getCheckUp().getEyes() + "\n" +
                        "Ouvidos: " + historyEntity.getCheckUp().getEars() + "\n" +
                        "Cavidade Oral: " + historyEntity.getCheckUp().getOralCavity() + "\n" +
                        "Sistema Respiratório: " + historyEntity.getCheckUp().getRespiratory() + "\n" +
                        "Sistema Circulatório: " + historyEntity.getCheckUp().getCirculatory() + "\n" +
                        "Sistema Hemolinfático: " + historyEntity.getCheckUp().getHemolymphatic() + "\n" +
                        "Sistema Digestório: " + historyEntity.getCheckUp().getDigestive() + "\n" +
                        "Sistema Genital: " + historyEntity.getCheckUp().getGenital() + "\n" +
                        "Sistema Urinário: " + historyEntity.getCheckUp().getUrinary() + "\n" +
                        "Sistema Nervoso: " + historyEntity.getCheckUp().getNervous() + "\n" +
                        "Pele e Anexos: " + historyEntity.getCheckUp().getSkinAndAppendages() + "\n" +
                        "Outros: " + historyEntity.getCheckUp().getOtherStatus() + "\n" +
                        "Observações Adicionais: " + historyEntity.getCheckUp().getOtherObservations() + "\n"
                )
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

        return mapToDTO(repository.save(history));
    }

    public HistoryResponseDTO update(Long id, HistoryRequestDTO requestDTO) {
        HistoryEntity history = findEntityById(id);
        AnimalEntity animal = animalRepository.findById(requestDTO.getIdAnimal())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));
        InitialCheckUpEntity checkUp = initialCheckUpRepository.findById(requestDTO.getIdLastCheckup())
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado."));

        history.setAnimal(animal);
        history.setCheckUp(checkUp);

        return mapToDTO(repository.save(history));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Histórico não encontrado.");
        }
        repository.deleteById(id);
    }

    public HistoryResponseDTO findById(Long id) {
        return mapToDTO(findEntityById(id));
    }

    public HistoryEntity findEntityById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Não foi encontrado o historico com o ID informado.")
        );
    }

    public Page<HistoryResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToDTO);
    }
}
