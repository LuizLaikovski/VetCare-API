package com.vetcare.petmeds.applicationPrimary.vaccine.controller;

import com.vetcare.petmeds.applicationPrimary.vaccine.entity.VaccineEntity;
import com.vetcare.petmeds.applicationPrimary.vaccine.service.VaccineService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccines")
@AllArgsConstructor
public class VaccineController implements VaccineControllerInterface {

    private VaccineService vaccineService;

    @Override
    public ResponseEntity<VaccineEntity> getVaccineById(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.getById(id));
    }

    @Override
    public ResponseEntity<Page<VaccineEntity>> getAllVaccines(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(vaccineService.getByName(name, pageable));
        }
        return ResponseEntity.ok(vaccineService.getAll(pageable));
    }

    @Override
    public ResponseEntity<VaccineEntity> createVaccine(@RequestBody VaccineEntity vaccine) {
        return new ResponseEntity<>(vaccineService.createVaccine(vaccine), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<VaccineEntity> updateVaccine(@PathVariable Long id, @RequestBody VaccineEntity vaccine) {
        vaccine.setId(id);
        return ResponseEntity.ok(vaccineService.updateVaccine(vaccine));
    }

    @Override
    public ResponseEntity<Void> deleteVaccine(@PathVariable Long id) {
        vaccineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
