package com.vetcare.petmeds.applicationPrimary.initialCheckUp.controller;

import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpResponseDTO;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.service.InitialCheckUpService;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@RestController
@RequestMapping("/initialCheckUp")
@AllArgsConstructor
@Tag(name = "Check-up Inicial", description = "Gerenciamento de rotas para os check-ups iniciais do animal")
public class InitialCheckUpController implements InitialCheckUpControllerInterface {
    private InitialCheckUpService service;

    @Override
    public ResponseEntity<InitialCheckUpResponseDTO> create(@RequestBody InitialCheckUpDTO checkUpDTO) {
        return new ResponseEntity<>(service.create(checkUpDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<InitialCheckUpResponseDTO> update(@PathVariable Long id, @RequestBody InitialCheckUpDTO checkUpDTO) {
        return ResponseEntity.ok(service.update(id, checkUpDTO));
    }

    @Override
    public ResponseEntity<Page<InitialCheckUpResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Override
    public ResponseEntity<InitialCheckUpResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    public ResponseEntity<Page<InitialCheckUpResponseDTO>> findByAnimalId(@PathVariable Long animalId, Pageable pageable) {
        return ResponseEntity.ok(service.findByAnimalId(animalId, pageable));
    }

    @Override
    public ResponseEntity<Page<InitialCheckUpResponseDTO>> findByVeterinarianId(@PathVariable Long veterinarianId, Pageable pageable) {
        return ResponseEntity.ok(service.findByVeterinarianId(veterinarianId, pageable));
    }

    @Override
    public ResponseEntity<Page<InitialCheckUpResponseDTO>> findByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByDateRange(start.atStartOfDay(), end.atTime(LocalTime.MAX), pageable));
    }

    @Override
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Checkup deletado com sucesso",
                "Checkup do ID " + id + " foi deletado com sucesso"
        );
        return ResponseEntity.ok(response);
    }
}
