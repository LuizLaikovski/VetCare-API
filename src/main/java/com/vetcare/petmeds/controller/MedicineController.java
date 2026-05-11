package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.Medicine;
import com.vetcare.petmeds.service.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
@AllArgsConstructor
public class MedicineController {

    private MedicineService medicineService;

    @GetMapping("/{id}")
    public Medicine getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @GetMapping("/name/{name}")
    public List<Medicine> getMedicineByName(@PathVariable String name, @RequestParam String medicineName) {
        return medicineService.getByName(medicineName);
    }

    @GetMapping("/all")
    public List<Medicine> getAllMedicine() { return medicineService.getAllMedicines(); }

    @PostMapping("/create")
    public Medicine createMedicine(@RequestBody Medicine medicine) {
        return medicineService.createMedicine(medicine);
    }

    @PostMapping("/createAll")
    public ResponseDTO createAllsMedicine(@RequestBody List<Medicine> medicine) {
        return medicineService.createAllsMedicine(medicine);
    }

    @PutMapping("/{id}")
    public Medicine updateMedicine(@PathVariable Long id, @RequestBody Medicine medicine) {
        return medicineService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteMedicine(@PathVariable Long id) {
        return medicineService.deleteMedicine(id);
    }
}
