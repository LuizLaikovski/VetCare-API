package com.vetcare.petmeds.archives.images.service;

import com.vetcare.petmeds.exception.ImageStorageException;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.archives.images.dto.ImageRequestDTO;
import com.vetcare.petmeds.archives.images.dto.ImageResponseDTO;
import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
import com.vetcare.petmeds.archives.images.entity.ImageEntity;
import com.vetcare.petmeds.archives.images.repository.ImageRepository;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.applicationPrimary.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;

    public ImageResponseDTO uploadImageToDTO(MultipartFile request) {
        try {
            return new ImageResponseDTO(imageRepository.save(new ImageEntity(
                    request.getOriginalFilename(),
                    request.getContentType(),
                    request.getBytes()
            )));
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao tentar realizar o update da imagem");
        }
    }

    public ImageEntity uploadImageToEntity(MultipartFile request) {
        try {
            return imageRepository.save(new ImageEntity(
                    request.getOriginalFilename(),
                    request.getContentType(),
                    request.getBytes()
            ));
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao tentar realizar o update da imagem");
        }
    }

    public ImageEntity updateImageEntity(Long id, MultipartFile request) {
        try {
            ImageEntity image = findImageByIdToEntity(id);
            image.setNome(request.getOriginalFilename());
            image.setTipoConteudo(request.getContentType());
            image.setDados(request.getBytes());
            return imageRepository.save(image);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao tentar realizar o update da imagem", e);
        }
    }

    public ImageResponseDTO findImageByIdToDTO(Long id) {
        try {
            return new ImageResponseDTO(imageRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada!")));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Imagem não encontrada!");
        }
    }

    public ImageEntity findImageByIdToEntity(Long id) {
        try {
            return imageRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada!"));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Imagem não encontrada!");
        }
    }

    public ResponseEntity<Resource> findByIdToImage(Long id) {
        try {
            ImageEntity image = findImageByIdToEntity(id);

            MediaType mediaType;
            try {
                mediaType = MediaType.parseMediaType(image.getTipoConteudo());
            } catch (Exception e) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(image.getDados().length)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment()
                                    .filename(image.getNome())
                                    .build().toString()
                            )
                    .body(new ByteArrayResource(image.getDados()));

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Imagem não encontrada!");
        }
    }

    public void deletedImage(Long id) {
        try {
            imageRepository.delete(findImageByIdToEntity(id));
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao deletar a imagem!", e);
        }
    }


}
