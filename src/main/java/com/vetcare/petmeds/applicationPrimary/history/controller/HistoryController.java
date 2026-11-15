package com.vetcare.petmeds.applicationPrimary.history.controller;

import com.vetcare.petmeds.applicationPrimary.history.dto.HistoryRequestDTO;
import com.vetcare.petmeds.applicationPrimary.history.dto.HistoryResponseDTO;
import com.vetcare.petmeds.applicationPrimary.history.service.HistoryService;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/history")
@AllArgsConstructor
@Tag(name = "Histórico", description = "Gerenciamento de rotas para o histórico do animal")
public class HistoryController implements HistoryControllerInterface {

    private HistoryService historyService;

    @Override
    public ResponseEntity<HistoryResponseDTO> create(@RequestBody HistoryRequestDTO requestDTO) {
        return new ResponseEntity<>(historyService.create(requestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HistoryResponseDTO> update(@PathVariable Long id, @RequestBody HistoryRequestDTO requestDTO) {
        return ResponseEntity.ok(historyService.update(id, requestDTO));
    }

    @Override
    public ResponseEntity<Page<HistoryResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(historyService.findAll(pageable));
    }

    @Override
    public ResponseEntity<HistoryResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(historyService.findById(id));
    }

    @Override
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        historyService.delete(id);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Histórico deletado com sucesso",
                "Histórico do ID " + id + " foi deletado com sucesso"
        );
        return ResponseEntity.ok(response);
    }
}
