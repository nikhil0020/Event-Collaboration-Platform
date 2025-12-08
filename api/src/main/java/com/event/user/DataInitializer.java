package com.event.user;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
  
  @Bean
  CommandLineRunner initUsers(UserRepository userRepository) {
    return (args) -> {
      if (userRepository.count() == 0) {
        User admin = User.builder()
                      .name("Admin User")
                      .email("admin@testmail.com")
                      .passwordHash("somehash")
                      .role(Role.ROLE_ADMIN)
                      .createdAt(LocalDateTime.now())
                      .build();

        userRepository.save(admin);
        System.out.println("Admin user created");
      }
    };
  }
}
