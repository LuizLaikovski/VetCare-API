package com.vetcare.petmeds.archives.fileTest.service;

import com.vetcare.petmeds.config.FileStorageConfig;
import com.vetcare.petmeds.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Path.of(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
        this.fileStorageLocation = path;
        logger.debug("Caminho do arquivo enviado: "+ this.fileStorageLocation);
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception exception) {
            throw new FileStorageException("Não foi possivel criar o diretório onde os arquivos serão salvos!");
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        logger.debug("Nome do arquivo enviado: "+ fileName);
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("O nome do arquivo enviado possui uma sequencia de caracteres inválidos!");
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.debug("funcionou o storeFile no service e irá retornar o :"+ fileName);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Não foi possivel salvar o arquivo no diretório!");
        }
    }
}
