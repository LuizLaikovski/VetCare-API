package com.vetcare.petmeds.modules.user.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.exception.UnauthorizedException;
import com.vetcare.petmeds.modules.token.service.TokenService;
import com.vetcare.petmeds.modules.user.dto.ResponseDTO;
import com.vetcare.petmeds.modules.user.dto.UserDTO;
import com.vetcare.petmeds.modules.user.dto.UserResponseDTO;
import com.vetcare.petmeds.modules.user.entity.TypeUser;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import com.vetcare.petmeds.modules.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    public ResponseDTO newUser(UserDTO user) {
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
        return new ResponseDTO("Usuario Criado com sucesso!");
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Este email não possui cadastro!"));
    }

    public ResponseDTO login(String email, String password) {
        UserEntity user = getUserByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Senha Incorreta!");
        }

        String generatedToken = tokenService.generateAndStoreToken(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getTypeUser()
        );

        return new ResponseDTO("Login realizado com sucesso!", userResponseDTO, generatedToken);
    }

    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        tokenService.revokeToken(token);
    }

    public ResponseDTO updateUser(Long id, UserEntity user) {
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

        return new ResponseDTO("O usuario foi atualizado com sucesso!");
    }

    public ResponseDTO deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }
        userRepository.deleteById(id);
        return new ResponseDTO("O usuario foi removido com sucesso!");
    }

}