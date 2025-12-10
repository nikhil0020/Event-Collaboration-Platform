# How to get current authenticated user access inside controller or service with a JWT token.

## Inside Controllers

### Get Authenticate User (No Manual Parsing)

```java
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserPrincipal principal) {
        // principal is the UserPrincipal we created earlier
        Long userId = principal.getId();
        String email = principal.getUsername();
        return ResponseEntity.ok(Map.of("id", userId, "email", email));
    }
}
```

## Inside Services

### Use SecurityContextHolder

```java
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

public class SecurityUtils {

    public static Optional<UserPrincipal> getCurrentUserPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();
        if (auth.getPrincipal() instanceof UserPrincipal up) {
            return Optional.of(up);
        }
        return Optional.empty();
    }

    public static Optional<Long> getCurrentUserId() {
        return getCurrentUserPrincipal().map(UserPrincipal::getId);
    }
}
```


### Above are the best practices, but to improve them a bit, we can use the below handy patterns

#### @CurrentUser meta-annotation for controllers

```java
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {}
```

```java
@GetMapping("/profile")
public ResponseEntity<?> profile(@CurrentUser UserPrincipal principal) { ... }
```

#### CurrentUserService Bean for services

```java
@Service
public class CurrentUserService {
    public Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new UnauthenticatedException("User not authenticated"));
    }
}
```
