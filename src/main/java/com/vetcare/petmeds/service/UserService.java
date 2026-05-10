package com.vetcare.petmeds.service;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.User;
import com.vetcare.petmeds.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public ResponseDTO newUser(User user) {
        userRepository.save(user);
        return new ResponseDTO("Usuario Criado com sucesso!");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public ResponseDTO login(String email, String password) {
        User user = getUserByEmail(email);

        if  (user == null) {
            return new ResponseDTO("Este email não possui cadastro!");
        }

        if (!user.getPassword().equals(password)) {
            return new ResponseDTO("Senha Incorreta!");
        }

        return new ResponseDTO("Login realizado com sucesso!");
    }

    public ResponseDTO updateUser(Long id, User user) {
        User oldUser = getUserById(id);

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