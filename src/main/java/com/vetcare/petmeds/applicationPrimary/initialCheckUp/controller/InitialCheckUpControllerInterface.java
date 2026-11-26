package com.vetcare.petmeds.applicationPrimary.initialCheckUp.controller;

import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpResponseDTO;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.doc    .InitialCheckUpDocumentDTO;
import com.vetcare.petmeds.shared.ErrorDTO;

public interface InitialCheckUpControllerInterface {

    @PostMapping
    @Operation(summary = "Cadastro de Check-up", description = "Cadastra um novo check-up inicial")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InitialCheckUpResponseDTO.class)
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
                    responseCode = "404",
                    description = "Not Found",
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
            )
    })
    ResponseEntity<InitialCheckUpResponseDTO> create(@RequestBody InitialCheckUpDTO checkUpDTO);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar check-up", description = "Atualiza os dados de um check-up existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InitialCheckUpResponseDTO.class)
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
                    responseCode = "404",
                    description = "Not Found",
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
            )
    })
    ResponseEntity<InitialCheckUpResponseDTO> update(@Parameter(description = "Identificador (ID) do registro") @PathVariable Long id, @RequestBody InitialCheckUpDTO checkUpDTO);

    @GetMapping
    @Operation(summary = "Listar todos os check-ups", description = "Retorna uma lista paginada de check-ups. Filtros opcionais: animalId, veterinarianId ou o período start/end (yyyy-MM-dd, informados juntos)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InitialCheckUpDocumentDTO.class)
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
                    responseCode = "404",
                    description = "Not Found",
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
            )
    })
    ResponseEntity<Page<InitialCheckUpResponseDTO>> findAll(
            @Parameter(description = "ID do animal") @RequestParam(required = false) Long animalId,
            @Parameter(description = "ID do veterinário") @RequestParam(required = false) Long veterinarianId,
            @Parameter(description = "Data inicial do período (yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @Parameter(description = "Data final do período (yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,@PageableDefault(page = 0, size = 10) Pageable pageable);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar check-up por ID", description = "Busca os detalhes de um check-up pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InitialCheckUpResponseDTO.class)
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
                    responseCode = "404",
                    description = "Not Found",
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
            )
    })
    ResponseEntity<InitialCheckUpResponseDTO> findById(@Parameter(description = "Identificador (ID) do registro") @PathVariable Long id);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover check-up", description = "Remove um check-up do sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
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
                    responseCode = "404",
                    description = "Not Found",
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
            )
    })
    ResponseEntity<ResponseDTO> delete(@Parameter(description = "Identificador (ID) do registro") @PathVariable Long id);
}
