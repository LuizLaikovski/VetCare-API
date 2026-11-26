package com.vetcare.petmeds.applicationPrimary.user.controller;

import com.vetcare.petmeds.applicationPrimary.user.dto.LoginRequestDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserProfileDTO;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import com.vetcare.petmeds.applicationPrimary.user.doc.UserDocumentDTO;
import com.vetcare.petmeds.shared.ErrorDTO;

public interface AuthControllerInterface {

    @PostMapping
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseLoginDTO.class)
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
            )
    })
    ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO);

    @DeleteMapping
    @Operation(summary = "Logout de usuário", description = "Realiza o logout do usuário atual")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseLoginDTO.class)
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
            )
    })
    ResponseEntity<ResponseLoginDTO> logout(@RequestHeader("Authorization") String authHeader);
}
