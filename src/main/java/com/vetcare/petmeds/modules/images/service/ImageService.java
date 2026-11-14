package com.vetcare.petmeds.modules.images.service;

import com.vetcare.petmeds.exception.ImageStorageException;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.images.dto.ImageRequestDTO;
import com.vetcare.petmeds.modules.images.dto.ImageResponseDTO;
import com.vetcare.petmeds.modules.images.dto.ImageUserResponseDTO;
import com.vetcare.petmeds.modules.images.entity.ImageEntity;
import com.vetcare.petmeds.modules.images.repository.ImageRepository;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import com.vetcare.petmeds.modules.user.repository.UserRepository;
import com.vetcare.petmeds.modules.user.service.UserService;
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

            user.setImage(image);
            userRepository.save(user);

            return new ImageUserResponseDTO(user, image);
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao tentar realizar o update da imagem");
        }
    }

    public ImageResponseDTO findImageByIdToDTO(Long id) {
        try {
            return new ImageResponseDTO(imageRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada!")));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Houve um erro ao encontrar a imagem!");
        }
    }

    public ImageEntity findImageByIdToEntity(Long id) {
        try {
            return imageRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada!"));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Houve um erro ao encontrar a imagem!");
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
            throw new ResourceNotFoundException("Houve um erro ao encontrar a imagem!");
        }
    }

}
