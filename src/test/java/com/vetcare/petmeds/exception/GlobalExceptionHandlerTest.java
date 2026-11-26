package com.vetcare.petmeds.exception;

import com.vetcare.petmeds.shared.ErrorDTO;
import com.vetcare.petmeds.shared.ErrorDetailsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final WebRequest request = new ServletWebRequest(new MockHttpServletRequest("GET", "/animals/1"));

    @Test
    void unauthorized_ShouldReturn401() {
        ResponseEntity<ErrorDTO> response = handler.handleUnauthorizedException(new UnauthorizedException("Senha Incorreta!"), request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Senha Incorreta!", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void resourceNotFound_ShouldReturn404() {
        ResponseEntity<ErrorDTO> response = handler.handleResourceNotFoundException(new ResourceNotFoundException("Animal não encontrado"), request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Animal não encontrado", response.getBody().getMessage());
    }

    @Test
    void fileNotFound_ShouldReturn404() {
        ResponseEntity<ErrorDTO> response = handler.handleFileNotFoundException(new FileNotFoundException("sem arquivo"), request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void generalException_ShouldReturn500WithDetails() {
        ResponseEntity<ErrorDetailsDTO> response = handler.handleGeneralException(new RuntimeException("falhou"), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno do servidor!", response.getBody().getMessage());
        assertEquals("falhou", response.getBody().getDetails());
    }

    @Test
    void badRequest_ShouldReturn400() {
        ResponseEntity<ErrorDTO> response = handler.handleBadRequestException(new org.apache.coyote.BadRequestException("ruim"), request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void typeMismatch_ShouldReturn400WithParameterName() {
        var ex = new org.springframework.web.method.annotation.MethodArgumentTypeMismatchException("abc", Long.class, "id", null, new NumberFormatException());

        ResponseEntity<ErrorDTO> response = handler.handleMethodArgumentTypeMismatchException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("id"));
        assertTrue(response.getBody().getMessage().contains("Long"));
    }

    @Test
    void imageAndFileStorage_ShouldReturnNoContent() {
        assertEquals(HttpStatus.NO_CONTENT, handler.handleImageStorageException(new ImageStorageException("x"), request).getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, handler.handleFileStorageException(new FileStorageException("x"), request).getStatusCode());
    }
}
