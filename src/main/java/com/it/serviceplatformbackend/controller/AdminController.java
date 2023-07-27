package com.it.serviceplatformbackend.controller;

import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.service.command.ApplicationServiceCommandService;
import com.it.serviceplatformbackend.service.query.UserQueryService;
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

    /*@GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userQueryService.getAllUsers(), HttpStatus.OK);
    }
    */
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
}
