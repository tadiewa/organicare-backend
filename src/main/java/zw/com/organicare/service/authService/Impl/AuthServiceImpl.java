/**
 * @author : tadiewa
 * date: 9/10/2025
 */


package zw.com.organicare.service.authService.Impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.com.organicare.constants.Branch;
import zw.com.organicare.constants.Role;
import zw.com.organicare.dto.user.AuthResponse;
import zw.com.organicare.dto.user.LoginRequest;
import zw.com.organicare.dto.user.UserDto;
import zw.com.organicare.exception.AlreadyExistsException;
import zw.com.organicare.exception.LoginFailed;
import zw.com.organicare.exception.RegistrationFailedException;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.security.JwtService;
import zw.com.organicare.service.authService.AuthService;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;

    @Override
    public UserDto register(UserDto request) {
        log.info("Processing registration request for email-------------------------------->: {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.error("Registration failed: Email already exists------------------------------->: {}", request.getEmail());
            throw new AlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .contactInfo(request.getContactInfo())
                .role(Role.valueOf(request.getRole().name()))
                .username(request.getUsername())
                .branch(Branch.valueOf(request.getBranch().name()))
                .isActive(false)
                .build();

        try {
            user = userRepository.save(user);
            log.info("User registered successfully----------------------------------->: {}", user.getEmail());
            return UserDto.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .fullName(user.getFullName())
                    .contactInfo(user.getContactInfo())
                    .role(Role.valueOf(user.getRole().name()))
                    .branch(Branch.valueOf(user.getBranch().name()))
                    .isActive(user.getIsActive())
                    .build();
        } catch (Exception e) {
            log.error("Error during user registration------------------------------------>: {}", e.getMessage());
            throw new RegistrationFailedException("Registration failed: " + e.getMessage());
        }
    }


    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Processing login request for email----------------------------->: {}", request.getUsername());

        try {
            // First check if the user exists
            var user = userRepository.findByEmail(request.getUsername())
                    .orElseThrow(() -> new LoginFailed("User not found"));

            // Then attempt authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            log.info("User authenticated successfully---------------------------->: {}", user.getEmail());
            String token = jwtService.generateToken(user);
            log.debug("JWT token generated for user----------------------->: {}", user.getEmail());

            return AuthResponse.builder()
                    .token(token)
                    .build();

        } catch (AuthenticationException e) {
            log.error("Authentication failed for email---------------------->: {}", request.getUsername());
            throw new LoginFailed("Invalid email or password" + e.getMessage());
        } catch (Exception e) {
            log.error("Error during login----------------------->: {}", e.getMessage());
            throw new LoginFailed("Login failed: " + e.getMessage());
        }
    }

    @Override
    public UserDto update(UserDto request) {
        log.info("Processing registration request for email-------------------------------->: {}", request.getEmail());

        userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.error("Update failed: Email not found------------------------------->: {}", request.getEmail());
                    return new UserNotFound("Email not found");
                });

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .contactInfo(request.getContactInfo())
                .role(Role.valueOf(request.getRole().name()))
                .username(request.getUsername())
                .branch(Branch.valueOf(request.getBranch().name()))
                .isActive(false)
                .build();

        try {
            user = userRepository.save(user);
            log.info("User registered successfully----------------------------------->: {}", user.getEmail());
            return UserDto.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .fullName(user.getFullName())
                    .contactInfo(user.getContactInfo())
                    .role(Role.valueOf(user.getRole().name()))
                    .branch(Branch.valueOf(user.getBranch().name()))
                    .isActive(user.getIsActive())
                    .build();
        } catch (Exception e) {
            log.error("Error during user registration------------------------------------>: {}", e.getMessage());
            throw new RegistrationFailedException("Registration failed: " + e.getMessage());
        }
    }

    @Override
    public User getAuthenticatedUser() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("JWT token missing");
        }

        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFound("User not found with username: " + username));
    }
}

