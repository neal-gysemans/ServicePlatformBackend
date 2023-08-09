package com.it.serviceplatformbackend.controller;

import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.dto.UpdateApplicationServiceCommand;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.dto.UserStatusResponse;
import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
import com.it.serviceplatformbackend.repository.UserRepository;
import com.it.serviceplatformbackend.service.command.ApplicationServiceCommandService;
import com.it.serviceplatformbackend.service.query.UserQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ApplicationServiceCommandService applicationServiceCommandService;
    private final UserRepository userRepository;
    private final ApplicationServiceRepository applicationServiceRepository;

    private final UserQueryService userQueryService;

    @GetMapping("/all-users")
    public ResponseEntity<List<UserStatusResponse>> getAllUsers() {
        return new ResponseEntity<>(userQueryService.getAllRegularUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/application-service/delete/{id}")
    public ResponseEntity<ApplicationServiceAndUserResponse> deleteApplicationService(@PathVariable long id) {
        // Call the service to delete the ApplicationService by its ID
        boolean deletionResult = applicationServiceCommandService.deleteApplicationServiceById(id);

        if (deletionResult) {
            // If deletion was successful, return HTTP status 200 (OK)
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // If deletion failed, return HTTP status 500 (Internal Server Error)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/toggle-active/{id}")
    public ResponseEntity<UserStatusResponse> toggleUserActive(@PathVariable long id) {
        // Find the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Toggle the active flag
        user.setActive(!user.isActive());

        // Save the updated user
        User updatedUser = userRepository.save(user);

        // Create and return a UserResponse based on the updated user
        UserStatusResponse userStatusResponse = UserStatusResponse.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .active(updatedUser.isActive())
                .build();
        return ResponseEntity.ok(userStatusResponse);
    }

    @PutMapping("/application-service/update")
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
