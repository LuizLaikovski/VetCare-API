package com.vetcare.petmeds.archives.fileTest.doc;

import com.vetcare.petmeds.archives.fileTest.dto.UploadFileResponseDTO;
import com.vetcare.petmeds.shared.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Arquivos", description = "Rotas de teste para upload de arquivos")
public interface FileStorageDoc {

    @Operation(summary = "Upload de arquivo", description = "Envia um arquivo ao servidor e retorna o nome, a URI de download, o tipo e o tamanho")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UploadFileResponseDTO.class)
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
    UploadFileResponseDTO uploadFile(@Parameter(description = "Arquivo a ser enviado (multipart/form-data)") MultipartFile file);

    List<UploadFileResponseDTO> uploadMultipleFile(MultipartFile[] file);

    ResponseEntity<ResponseEntity> downloadFile(String filename, HttpServletRequest request);

}
