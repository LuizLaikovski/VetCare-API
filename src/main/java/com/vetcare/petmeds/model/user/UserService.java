package com.vetcare.petmeds.model.user;

import com.vetcare.petmeds.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public ResponseDTO newUser(UserEntity user) {
        userRepository.save(user);
        return new ResponseDTO("Usuario Criado com sucesso!");
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public ResponseDTO login(String email, String password) {
        UserEntity user = getUserByEmail(email);

        if  (user == null) {
            return new ResponseDTO("Este email não possui cadastro!");
        }

        if (!user.getPassword().equals(password)) {
            return new ResponseDTO("Senha Incorreta!");
        }

        return new ResponseDTO("Login realizado com sucesso!");
    }

    public ResponseDTO updateUser(Long id, UserEntity user) {
        UserEntity oldUser = getUserById(id);

        if (oldUser == null) {
            return new ResponseDTO("Usuário não encontrado!");
        }

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }

        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            oldUser.setPassword(user.getPassword());
        }

        if (user.getTypeUser() != null) {
            oldUser.setTypeUser(user.getTypeUser());
        }

        userRepository.save(oldUser);

        return new ResponseDTO("O usuario foi atualizado com sucesso!");
    }

    public ResponseDTO deleteUser(Long id) {
        userRepository.deleteById(id);
        return new ResponseDTO("O usuario foi removido com sucesso!");
    }

}