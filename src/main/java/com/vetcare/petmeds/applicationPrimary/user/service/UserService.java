package com.vetcare.petmeds.applicationPrimary.user.service;

import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
import com.vetcare.petmeds.archives.images.entity.ImageEntity;
import com.vetcare.petmeds.archives.images.service.ImageService;
import com.vetcare.petmeds.exception.ImageStorageException;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.exception.UnauthorizedException;
import com.vetcare.petmeds.applicationPrimary.token.service.TokenService;
import com.vetcare.petmeds.applicationPrimary.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserProfileDTO;
import com.vetcare.petmeds.applicationPrimary.user.entity.TypeUser;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.applicationPrimary.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private ImageService imageService;

    public ResponseLoginDTO newUser(UserDTO user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getTypeUser() == null) {
            userEntity.setTypeUser(TypeUser.CLIENT);
        } else {
            userEntity.setTypeUser(user.getTypeUser());
        }

        userRepository.save(userEntity);
        return new ResponseLoginDTO("Usuario Criado com sucesso!");
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
    }

    public UserProfileDTO getUserByIdToDTO(Long id) {
        return new UserProfileDTO(
                getUserById(id),
                imageService
        );
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Este email não possui cadastro!"));
    }

    public ResponseLoginDTO login(String email, String password) {
        UserEntity user = getUserByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Senha Incorreta!");
        }

        String generatedToken = tokenService.generateAndStoreToken(user);

        UserProfileDTO userProfileDTO = new UserProfileDTO(user, imageService);

        return new ResponseLoginDTO("Login realizado com sucesso!", userProfileDTO, generatedToken);
    }

    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        tokenService.revokeToken(token);
    }

    public ResponseLoginDTO updateUser(Long id, UserEntity user) {
        UserEntity oldUser = getUserById(id);

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }

        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getTypeUser() != null) {
            oldUser.setTypeUser(user.getTypeUser());
        }

        userRepository.save(oldUser);
        return new ResponseLoginDTO("O usuario foi atualizado com sucesso!");
    }

    public ResponseLoginDTO deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }
        userRepository.deleteById(id);
        return new ResponseLoginDTO("O usuario foi removido com sucesso!");
    }

    public void deletedImageToUser(Long id) {
        try {
            UserEntity user = getUserById(id);
            if (user.getImageId() == null) {
                throw new ResourceNotFoundException("Usuário não possui imagem cadastrada!");
            }
            ImageEntity image = imageService.findImageByIdToEntity(user.getImageId());

            user.setImageId(null);
            userRepository.save(user);

            imageService.deletedImage(image.getId());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao deletar a imagem!", e);
        }
    }

    public ImageUserResponseDTO uploadImageToUser(Long id, MultipartFile imageFile) {
        try {
            UserEntity user = getUserById(id);

            ImageEntity image = imageService.uploadImageToEntity(imageFile);

            user.setImageId(image.getId());
            userRepository.save(user);

            return new ImageUserResponseDTO(user, image);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao fazer upload da imagem!", e);
        }
    }

    public ImageUserResponseDTO updateImageToUser(Long id, MultipartFile imageFile) {
        try {
            UserEntity user = getUserById(id);
            if (user.getImageId() == null) {
                throw new ResourceNotFoundException("Usuário não possui imagem cadastrada!");
            }

            ImageEntity image = imageService.updateImageEntity(user.getImageId(), imageFile);

            return new ImageUserResponseDTO(user, image);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao atualizar a imagem!", e);
        }
    }

    public ResponseEntity<Resource> getByImage(Long id) {
        try {
            UserEntity user = getUserById(id);
            if (user.getImageId() == null) {
                throw new ResourceNotFoundException("Usuário não possui imagem cadastrada!");
            }
            ImageEntity image = imageService.findImageByIdToEntity(user.getImageId());

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
        }
    }

}
