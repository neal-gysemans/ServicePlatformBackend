package com.it.serviceplatformbackend.service.command;

import com.it.serviceplatformbackend.auth.AuthenticationService;
import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.domain.Booking;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.dto.NewApplicationServiceCommand;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
import com.it.serviceplatformbackend.repository.BookingRepository;
import com.it.serviceplatformbackend.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceCommandService {
    private final ApplicationServiceRepository applicationServiceRepository;
    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public boolean deleteApplicationServiceById(Long id) {
        List<Booking> bookingsWithTheServiceToBeDeleted = bookingRepository.findByApplicationService_Id(id);

        try {
            for (Booking booking : bookingsWithTheServiceToBeDeleted) {
                bookingRepository.deleteById(booking.getId());
            }
            applicationServiceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApplicationServiceAndUserResponse createNewApplicationService(NewApplicationServiceCommand newApplicationServiceCommand,
                                                                         HttpServletRequest request) {
        String email = authenticationService.getEmailFromToken(request);

        if (applicationServiceRepository.findByServiceNameAndUserEmail(
                newApplicationServiceCommand.getName(),
                email).isPresent()) {
            throw new EntityExistsException("This user already has a service with that name");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user found"));

        ApplicationService newApplicationService = ApplicationService.builder()
                .name(newApplicationServiceCommand.getName())
                .description(newApplicationServiceCommand.getDescription())
                .cost(newApplicationServiceCommand.getCost())
                .active(true)
                .user(user)
                .build();

        newApplicationService = applicationServiceRepository.save(newApplicationService);

        UserResponse serviceProvider = UserResponseBuilder(newApplicationService.getUser());
        return ApplicationServiceAndUserResponse.builder()
                .id(newApplicationService.getId())
                .name(newApplicationServiceCommand.getName())
                .description(newApplicationServiceCommand.getDescription())
                .serviceProvider(serviceProvider)
                .cost(newApplicationServiceCommand.getCost())
                .build();
    }

    private UserResponse UserResponseBuilder(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
