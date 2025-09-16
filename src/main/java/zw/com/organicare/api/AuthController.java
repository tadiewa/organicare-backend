/**
 * @author : tadiewa
 * date: 9/10/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.com.organicare.dto.user.AuthResponse;
import zw.com.organicare.dto.user.LoginRequest;
import zw.com.organicare.dto.user.UserDto;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.service.authService.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j

public class AuthController {
    private final AuthService userService;

    @PostMapping(value ="/register/user")
    public ResponseEntity<UserDto> register(@RequestBody UserDto request) {
        UserDto response = userService.register(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Login request received for email--------------------------------->: {}", request.getUsername());
        log.info("Login request received for password----------------------------->: {}", request.getPassword());
        return ResponseEntity.ok(userService.login(request));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserDto request) {
        UserDto response = userService.update(request);
        return ResponseEntity.ok(response);
    }
}
