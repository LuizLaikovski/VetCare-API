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
import com.vetcare.petmeds.applicationPrimary.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public ImageResponseDTO uploadImageToDTO(ImageRequestDTO request) {
        try {
            return new ImageResponseDTO(imageRepository.save(new ImageEntity(
                    request.getName(),
                    request.getTypeContent(),
                    request.getData().getBytes()
            )));
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao tentar realizar o update da imagem");
        }
    }

    public ImageUserResponseDTO uploadImageToUser(Long id, ImageRequestDTO request) {
        try {
            UserEntity user = userService.getUserById(id);

            ImageEntity image = imageRepository.save(new ImageEntity(
                    request.getName(),
                    request.getTypeContent(),
                    request.getData().getBytes()
            ));

            user.setImageId(image.getId());
            userRepository.save(user);

            return new ImageUserResponseDTO(user, image);
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao tentar realizar o upload da imagem");
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

    public ResponseEntity<byte[]> findByIdToImage(Long id) {
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
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getNome() + "\"")
                    .body(image.getDados());

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Imagem não encontrada!");
        }
    }

}
