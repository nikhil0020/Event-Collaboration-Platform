package com.eventcollab.auth;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventcollab.auth.dto.AuthResponse;
import com.eventcollab.auth.dto.LoginRequest;
import com.eventcollab.auth.dto.RegisterRequest;
import com.eventcollab.security.JwtTokenProvider;
import com.eventcollab.user.Role;
import com.eventcollab.user.User;
import com.eventcollab.user.UserRepository;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthContoller {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      return ResponseEntity.badRequest().body("Email is already taken");
    }

    User user = User.builder()
                  .name(request.getName())
                  .email(request.getEmail())
                  .passwordHash(passwordEncoder.encode(request.getPassword()))
                  .role(Role.ROLE_USER)
                  .createdAt(LocalDateTime.now())
                  .build();
    userRepository.save(user);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    String token = jwtTokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
  }
  
}
