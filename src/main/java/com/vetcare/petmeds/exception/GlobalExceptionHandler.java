package com.vetcare.petmeds.exception;

import com.vetcare.petmeds.shared.ErrorDTO;
import com.vetcare.petmeds.shared.ErrorDetailsDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
            new Date(),
            "A rota inserida é inválida!",
            request.getDescription(false)
        );

         return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
     }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetailsDTO> handleGeneralException(Exception ex, WebRequest request) {
        ErrorDetailsDTO errorDTO = new ErrorDetailsDTO(
                new Date(),
                "Erro interno do servidor!",
                request.getDescription(false),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                "Rota não encontrada",
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                "Parâmetro inválido: " + ex.getName() + " deve ser do tipo " + ex.getRequiredType().getSimpleName(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageStorageException.class)
    public final ResponseEntity<ErrorDTO> handleImageStorageException(ImageStorageException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<ErrorDTO> handleFileStorageException(FileStorageException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<ErrorDTO> handleFileNotFoundException(FileNotFoundException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}

