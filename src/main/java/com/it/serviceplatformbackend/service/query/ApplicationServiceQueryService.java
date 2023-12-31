package com.it.serviceplatformbackend.service.query;

import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceQueryService {
    private final ApplicationServiceRepository applicationServiceRepository;

    public List<ApplicationServiceAndUserResponse> getAllServices() {
        return applicationServiceRepository.findAll()
                .stream()
                .map(this::ApplicationServiceAndUserResponseBuilder)
                .collect(Collectors.toList());
    }

    public List<ApplicationServiceAndUserResponse> getPersonalServices(String userEmail) {
        return applicationServiceRepository.findByUserEmail(userEmail)
                .stream()
                .map(this::ApplicationServiceAndUserResponseBuilder)
                .collect(Collectors.toList());
    }


    private ApplicationServiceAndUserResponse ApplicationServiceAndUserResponseBuilder(ApplicationService applicationService) {
        UserResponse userResponse = UserResponseBuilder(applicationService.getUser());
        return ApplicationServiceAndUserResponse.builder()
                .id(applicationService.getId())
                .name(applicationService.getName())
                .cost(applicationService.getCost())
                .description(applicationService.getDescription())
                .serviceProvider(userResponse)
                .build();
    }

    private UserResponse UserResponseBuilder(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
