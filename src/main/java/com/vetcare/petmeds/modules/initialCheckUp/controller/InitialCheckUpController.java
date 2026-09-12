package com.vetcare.petmeds.modules.initialCheckUp.controller;

import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpResponseDTO;
import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.modules.initialCheckUp.service.InitialCheckUpService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/initialCheckUp")
@AllArgsConstructor
public class InitialCheckUpController {
    private InitialCheckUpService service;

    @PostMapping("/create")
    public ResponseEntity<InitialCheckUpResponseDTO> create(@RequestBody InitialCheckUpDTO checkUpDTO) {
        return ResponseEntity.ok(service.create(checkUpDTO));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<InitialCheckUpResponseDTO> update(@PathVariable Long id, @RequestBody InitialCheckUpDTO checkUpDTO) {
        return ResponseEntity.ok(service.update(id, checkUpDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InitialCheckUpResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InitialCheckUpResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<InitialCheckUpResponseDTO>> findByAnimalId(@PathVariable Long animalId) {
        return ResponseEntity.ok(service.findByAnimalId(animalId));
    }

    @GetMapping("/vet/{veterinarianId}")
    public ResponseEntity<List<InitialCheckUpResponseDTO>> findByVeterinarianId(@PathVariable Long veterinarianId) {
        return ResponseEntity.ok(service.findByVeterinarianId(veterinarianId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<InitialCheckUpResponseDTO>> findByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return ResponseEntity.ok(service.findByDateRange(start.atStartOfDay(), end.atTime(LocalTime.MAX)));
    }
}
