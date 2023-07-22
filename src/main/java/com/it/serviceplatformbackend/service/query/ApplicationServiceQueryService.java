package com.it.serviceplatformbackend.service.query;

import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RolesAllowed("USER")
@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class ApplicationServiceQueryService {
    private final ApplicationServiceRepository applicationServiceRepository;

    public List<ApplicationServiceAndUserResponse> getAllServices() {
        return applicationServiceRepository.findAll()
                .stream()
                .map(this::ApplicationServiceAndUserResponseBuilder)
                .collect(Collectors.toList());
    }

    private ApplicationServiceAndUserResponse ApplicationServiceAndUserResponseBuilder(ApplicationService applicationService) {
        UserResponse userResponse = UserResponseBuilder(applicationService.getUser());
        return ApplicationServiceAndUserResponse.builder()
                .name(applicationService.getName())
                .cost(applicationService.getCost())
                .description(applicationService.getDescription())
                .userResponse(userResponse)
                .build();
    }

    private UserResponse UserResponseBuilder(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }


}
