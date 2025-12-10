package com.eventcollab.security;

import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
  public Long getCurrentUserId() {
    return SecurityUtils.getCurrentUserId().orElseThrow(
      () -> new UnauthorizedException("User not authenticated")
    );
  }
}
