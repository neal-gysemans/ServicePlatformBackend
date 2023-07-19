package com.it.serviceplatformbackend.service.query;

import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RolesAllowed("USER")
@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class ApplicationServiceQueryService {
    private final ApplicationServiceRepository applicationServiceRepository;

    public List<ApplicationService> getAllServices() {
        return applicationServiceRepository.findAll();
    }
}
