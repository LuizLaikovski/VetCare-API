package com.vetcare.petmeds.modules.medicine.controller;

import com.vetcare.petmeds.modules.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.medicine.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
@AllArgsConstructor
@Tag(name = "Medicamentos", description = "Gerenciamento de rotas para os medicamentos")
public class MedicineController {

    private MedicineService medicineService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID", description = "Busca os detalhes de um medicamento pelo seu ID")
    public ResponseEntity<MedicineEntity> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar medicamento por nome", description = "Busca medicamentos pelo nome")
    public ResponseEntity<List<MedicineEntity>> getMedicineByName(@PathVariable String name) {
        return ResponseEntity.ok(medicineService.getByName(name));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os medicamentos", description = "Retorna uma lista paginada de medicamentos")
    public ResponseEntity<Page<MedicineEntity>> getAllMedicine(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(medicineService.getAllMedicines(pageable));
    }

    @PostMapping("/create")
    @Operation(summary = "Cadastro de Medicamento", description = "Cadastra um novo medicamento")
    public ResponseEntity<MedicineEntity> createMedicine(@RequestBody MedicineEntity medicine) {
        return new ResponseEntity<>(medicineService.createMedicine(medicine), HttpStatus.CREATED);
    }

    @PostMapping("/createAll")
    @Operation(summary = "Cadastro de múltiplos medicamentos", description = "Cadastra uma lista de novos medicamentos")
    public ResponseEntity<ResponseLoginDTO> createAllsMedicine(@RequestBody List<MedicineEntity> medicine) {
        return new ResponseEntity<>(medicineService.createAllsMedicine(medicine), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicamento", description = "Atualiza os dados de um medicamento existente")
    public ResponseEntity<MedicineEntity> updateMedicine(@PathVariable Long id, @RequestBody MedicineEntity medicine) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, medicine));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover medicamento", description = "Remove um medicamento do sistema")
    public ResponseEntity<ResponseLoginDTO> deleteMedicine(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.deleteMedicine(id));
    }
}
