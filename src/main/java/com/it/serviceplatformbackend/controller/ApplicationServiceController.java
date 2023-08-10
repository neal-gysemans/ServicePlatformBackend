package com.it.serviceplatformbackend.controller;

import com.it.serviceplatformbackend.auth.AuthenticationService;
import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.dto.NewApplicationServiceCommand;
import com.it.serviceplatformbackend.dto.UpdateApplicationServiceCommand;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
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
    private final ApplicationServiceRepository applicationServiceRepository;

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationServiceAndUserResponse> deleteApplicationService(@PathVariable long id) {
        // Call the service to delete the ApplicationService by its ID
        boolean deletionResult = applicationServiceCommandService.deleteApplicationServiceById(id);

        if (deletionResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<ApplicationServiceAndUserResponse> updateApplicationService(
            @RequestBody UpdateApplicationServiceCommand updateApplicationServiceCommand
    ) {
        // Find the service by ID
        ApplicationService service = applicationServiceRepository.findById(updateApplicationServiceCommand.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + updateApplicationServiceCommand.getId()));

        // make the changes
        service.setName(updateApplicationServiceCommand.getName());
        service.setCost(updateApplicationServiceCommand.getCost());
        service.setDescription(updateApplicationServiceCommand.getDescription());

        // Save the updated service
        ApplicationService updatedService = applicationServiceRepository.save(service);

        UserResponse userResponse = UserResponse.builder()
                .id(updatedService.getUser().getId())
                .email(updatedService.getUser().getEmail())
                .name(updatedService.getUser().getName())
                .build();

        // Create and return a ApplicationServiceResponse based on the updated service
        ApplicationServiceAndUserResponse applicationServiceAndUserResponse = ApplicationServiceAndUserResponse.builder()
                .id(updatedService.getId())
                .name(updatedService.getName())
                .description(updatedService.getDescription())
                .cost(updatedService.getCost())
                .serviceProvider(userResponse)
                .build();

        return ResponseEntity.ok(applicationServiceAndUserResponse);
    }
}
