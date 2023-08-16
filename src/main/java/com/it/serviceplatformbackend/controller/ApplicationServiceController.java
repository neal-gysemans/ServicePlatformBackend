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
        String email = authenticationService.getEmailFromToken(request);
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationServiceAndUserResponse> deleteApplicationService(@PathVariable long id) {
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
        ApplicationService service = applicationServiceRepository.findById(updateApplicationServiceCommand.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + updateApplicationServiceCommand.getId()));

        service.setName(updateApplicationServiceCommand.getName());
        service.setCost(updateApplicationServiceCommand.getCost());
        service.setDescription(updateApplicationServiceCommand.getDescription());

        ApplicationService updatedService = applicationServiceRepository.save(service);

        UserResponse userResponse = UserResponse.builder()
                .id(updatedService.getUser().getId())
                .email(updatedService.getUser().getEmail())
                .name(updatedService.getUser().getName())
                .build();

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
