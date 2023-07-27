package com.it.serviceplatformbackend.controller;

import com.it.serviceplatformbackend.auth.AuthenticationService;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.service.query.ApplicationServiceQueryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RolesAllowed("USER")
@RequiredArgsConstructor
@RequestMapping("/application-service")
public class ApplicationServiceController {
    private final ApplicationServiceQueryService applicationServiceQueryService;
    private final AuthenticationService authenticationService;

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationServiceAndUserResponse>> getAllApplicationServices() {
        return new ResponseEntity<>(applicationServiceQueryService.getAllServices(), HttpStatus.OK);
    }

    @GetMapping("/personal")
    public ResponseEntity<List<ApplicationServiceAndUserResponse>> getPersonalApplicationServices(HttpServletRequest request) {
        // Get the email from the JWT token in the request
        String email = authenticationService.getEmailFromToken(request);
        System.out.println(email);
        // Call the service method to get the user's personal services
        List<ApplicationServiceAndUserResponse> personalServices = applicationServiceQueryService.getPersonalServices(email);

        return new ResponseEntity<>(personalServices, HttpStatus.OK);
    }

}
