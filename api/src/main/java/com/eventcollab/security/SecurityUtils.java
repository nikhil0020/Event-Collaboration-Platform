package com.eventcollab.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
  public static Optional<UserPrincipal> getCurrentUserPrincipal() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if ( auth == null || !auth.isAuthenticated()) return Optional.empty();

    if ( auth.getPrincipal() instanceof UserPrincipal up){
      return Optional.of(up);
    }
    return Optional.empty();
  }

  public static Optional<Long> getCurrentUserId() {
    return getCurrentUserPrincipal().map(UserPrincipal::getId);
  }
}
