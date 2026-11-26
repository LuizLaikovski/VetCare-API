package com.vetcare.petmeds.config;

import com.vetcare.petmeds.applicationPrimary.user.entity.TypeUser;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.applicationPrimary.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Garante que exista um usuário ADM sempre que a aplicação sobe.
 * <p>
 * Como o cadastro de usuários exige token, este usuário é a porta de entrada de um banco novo.
 * A criação é idempotente: se o e-mail já existe, nada é alterado (a senha atual é preservada).
 * Os dados podem ser trocados por ADMIN_NAME, ADMIN_EMAIL e ADMIN_PASSWORD.
 */
@Component
public class AdminUserInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String name;
    private final String email;
    private final String password;

    public AdminUserInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.name:Administrador}") String name,
            @Value("${app.admin.email:admin@email.com}") String email,
            @Value("${app.admin.password:Admin123}") String password
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByEmail(email).isPresent()) {
            logger.info("Usuário ADM {} já existe; nada a criar", email);
            return;
        }

        UserEntity admin = new UserEntity();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setTypeUser(TypeUser.ADM);
        userRepository.save(admin);

        logger.info("Usuário ADM {} criado", email);
    }
}
