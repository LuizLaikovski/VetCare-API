package com.vetcare.petmeds.applicationPrimary.medicine.controller;

import com.vetcare.petmeds.applicationPrimary.medicine.dto.MedicineResponseDTO;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicine")
@AllArgsConstructor
@Tag(name = "Medicamentos", description = "Gerenciamento de rotas para os medicamentos")
public class MedicineController implements MedicineControllerInterface {

    private MedicineService medicineService;

    @Override
    public ResponseEntity<MedicineResponseDTO> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @Override
    public ResponseEntity<Page<MedicineResponseDTO>> getMedicineByName(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(medicineService.getByName(name, pageable));
    }

    @Override
    public ResponseEntity<Page<MedicineResponseDTO>> getAllMedicine(Pageable pageable) {
        return ResponseEntity.ok(medicineService.getAllMedicines(pageable));
    }

    @Override
    public ResponseEntity<MedicineResponseDTO> createMedicine(@RequestBody MedicineEntity medicine) {
        return new ResponseEntity<>(medicineService.createMedicine(medicine), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MedicineResponseDTO> updateMedicine(@PathVariable Long id, @RequestBody MedicineEntity medicine) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, medicine));
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteMedicine(@PathVariable Long id) {
        return new ResponseEntity<>(medicineService.deleteMedicine(id), HttpStatus.NO_CONTENT);
    }
}
