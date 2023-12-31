package com.it.serviceplatformbackend.auth;

import com.it.serviceplatformbackend.config.JwtService;
import com.it.serviceplatformbackend.domain.Role;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.exception.InactiveUserException;
import com.it.serviceplatformbackend.exception.InvalidCredentialsException;
import com.it.serviceplatformbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already exists.");
        }

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .active(true)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws InactiveUserException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            // Throw custom exception for invalid credentials
            throw new InvalidCredentialsException("Invalid login credentials");
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        if (!user.isActive()) {
            throw new InactiveUserException("User is not active");
        }

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String getEmailFromToken(HttpServletRequest request) {
        // Extract the token from the "Authorization" header
        String authHeader = request.getHeader("Authorization");
        String jwt;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            // Use your JwtService to extract the email from the token
            email = jwtService.extractUsername(jwt);
        }

        return email;
    }
}
