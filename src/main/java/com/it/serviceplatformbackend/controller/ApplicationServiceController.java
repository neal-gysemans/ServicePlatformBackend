package com.it.serviceplatformbackend.controller;

import com.it.serviceplatformbackend.auth.AuthenticationService;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.dto.NewApplicationServiceCommand;
import com.it.serviceplatformbackend.service.command.ApplicationServiceCommandService;
import com.it.serviceplatformbackend.service.query.ApplicationServiceQueryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RolesAllowed("USER")
@RequiredArgsConstructor
@RequestMapping("/application-service")
public class ApplicationServiceController {
    private final ApplicationServiceQueryService applicationServiceQueryService;
    private final AuthenticationService authenticationService;
    private final ApplicationServiceCommandService applicationServiceCommandService;

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationServiceAndUserResponse>> getAllApplicationServices() {
        return new ResponseEntity<>(applicationServiceQueryService.getAllServices(), HttpStatus.OK);
    }

    @GetMapping("/personal")
    public ResponseEntity<List<ApplicationServiceAndUserResponse>> getPersonalApplicationServices(HttpServletRequest request) {
        // Get the email from the JWT token in the request
        String email = authenticationService.getEmailFromToken(request);
        //System.out.println(email);
        // Call the service method to get the user's personal services
        List<ApplicationServiceAndUserResponse> personalServices = applicationServiceQueryService.getPersonalServices(email);

        return new ResponseEntity<>(personalServices, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApplicationServiceAndUserResponse> createNewService(
            @RequestBody NewApplicationServiceCommand newApplicationServiceCommand,
            HttpServletRequest request) {

        try {
            ApplicationServiceAndUserResponse createdService = applicationServiceCommandService.createNewApplicationService(newApplicationServiceCommand, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // You can return an error message if needed
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // You can return an error message if needed
        }
    }
}
