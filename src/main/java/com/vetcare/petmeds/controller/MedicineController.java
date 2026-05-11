package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.medicine.MedicineEntity;
import com.vetcare.petmeds.model.medicine.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
@AllArgsConstructor
public class MedicineController {

    private MedicineService medicineService;

    @GetMapping("/{id}")
    public MedicineEntity getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @GetMapping("/name/{name}")
    public List<MedicineEntity> getMedicineByName(@PathVariable String name, @RequestParam String medicineName) {
        return medicineService.getByName(medicineName);
    }

    @GetMapping("/all")
    public List<MedicineEntity> getAllMedicine() { return medicineService.getAllMedicines(); }

    @PostMapping("/create")
    public MedicineEntity createMedicine(@RequestBody MedicineEntity medicine) {
        return medicineService.createMedicine(medicine);
    }

    @PostMapping("/createAll")
    public ResponseDTO createAllsMedicine(@RequestBody List<MedicineEntity> medicine) {
        return medicineService.createAllsMedicine(medicine);
    }

    @PutMapping("/{id}")
    public MedicineEntity updateMedicine(@PathVariable Long id, @RequestBody MedicineEntity medicine) {
        return medicineService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteMedicine(@PathVariable Long id) {
        return medicineService.deleteMedicine(id);
    }
}
