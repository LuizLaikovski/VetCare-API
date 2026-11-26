package com.vetcare.petmeds.applicationPrimary.vaccinationRecord.controller;

import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.doc.VaccinationRecordDocumentDTO;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.RequestEditVaccinationRecord;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordCreateDTO;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordResponseDTO;
import com.vetcare.petmeds.shared.ErrorDTO;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

public interface VaccinationRecordControllerInterface {

    @PostMapping
    @Operation(
            summary = "Cadastrar registro de vacina",
            description = "Cadastra um novo registro de aplicação de vacina no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    ResponseEntity<ResponseDTO> create(@RequestBody VaccinationRecordCreateDTO newVaccinationRecord);

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar registro de vacina por ID",
            description = "Busca as informações detalhadas de um registro de vacina através do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VaccinationRecordResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    ResponseEntity<VaccinationRecordResponseDTO> findById(@PathVariable Long id);

    @GetMapping
    @Operation(
            summary = "Listar todos os registros de vacinas",
            description = "Retorna uma lista paginada de registros de vacina. Filtros opcionais: animalId, medicId, vaccineId, status, date ou o período startDate/endDate (yyyy-MM-dd, informados juntos)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VaccinationRecordDocumentDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    ResponseEntity<Page<VaccinationRecordResponseDTO>> getAllVaccinationRecord(
            @Parameter(description = "ID do animal") @RequestParam(required = false) Long animalId,
            @Parameter(description = "ID do médico") @RequestParam(required = false) Long medicId,
            @Parameter(description = "ID da vacina") @RequestParam(required = false) Long vaccineId,
            @Parameter(description = "Status da aplicação") @RequestParam(required = false) String status,
            @Parameter(description = "Data exata da aplicação (yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Data inicial do período (yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Data final do período (yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar registro de vacina",
            description = "Atualiza os dados de um registro de vacina existente através do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VaccinationRecordResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    ResponseEntity<VaccinationRecordResponseDTO> edit(@Valid @RequestBody RequestEditVaccinationRecord request, @PathVariable Long id);
}
