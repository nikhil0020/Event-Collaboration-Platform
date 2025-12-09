package com.eventcollab.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserContoller {
  
  @GetMapping("/me")
  public String me(Authentication authentication) {
    return authentication.getName();
  }
}
