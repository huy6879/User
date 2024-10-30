package com.giahuy.demo.configuration;


import com.giahuy.demo.constant.PredefinedRole;
import com.giahuy.demo.entity.User;
import com.giahuy.demo.enums.Role;
import com.giahuy.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig  {

    PasswordEncoder passwordEncoder;

    public ApplicationInitConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder; // Khởi tạo ở đây
    }

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
               var roles = new HashSet<String>();
               roles.add(PredefinedRole.ADMIN_ROLE);

               User user = User.builder()
                       .username("admin")
                       .password(passwordEncoder.encode("admin"))
                       //.roles(roles)
                       .build();

               userRepository.save(user); //persist
                log.warn("admin user has been created with default");
            }
        };
    }
}
