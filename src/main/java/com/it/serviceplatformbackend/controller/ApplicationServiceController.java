package com.it.serviceplatformbackend.controller;

import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.service.query.ApplicationServiceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application-service")
public class ApplicationServiceController {
    private final ApplicationServiceQueryService applicationServiceQueryService;

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationServiceAndUserResponse>> getAllApplicationServices() {
        return new ResponseEntity<>(applicationServiceQueryService.getAllServices(), HttpStatus.OK);
    }
}
