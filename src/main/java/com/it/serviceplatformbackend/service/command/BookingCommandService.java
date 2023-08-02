package com.it.serviceplatformbackend.service.command;

import com.it.serviceplatformbackend.auth.AuthenticationService;
import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.domain.Booking;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceResponse;
import com.it.serviceplatformbackend.dto.BookingResponse;
import com.it.serviceplatformbackend.dto.CreatedBookingResponse;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
import com.it.serviceplatformbackend.repository.BookingRepository;
import com.it.serviceplatformbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingCommandService {
    private final BookingRepository bookingRepository;
    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final ApplicationServiceRepository applicationServiceRepository;


    public CreatedBookingResponse createBooking(Long serviceId, HttpServletRequest request) {
        User serviceBooker = userRepository.findByEmail(authenticationService.getEmailFromToken(request))
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        ApplicationService bookedService = applicationServiceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found."));

        Booking booking = Booking.builder()
                .applicationService(bookedService)
                .user(serviceBooker)
                .dateTime(LocalDateTime.now())
                .build();

        bookingRepository.save(booking);

        CreatedBookingResponse bookingResponse = getCreatedBookingResponse(serviceBooker, bookedService, booking);
        return bookingResponse;
    }

    private static CreatedBookingResponse getCreatedBookingResponse(User serviceBooker, ApplicationService bookedService, Booking booking) {
        // Building the response
        UserResponse userResponse = UserResponse.builder()
                .name(serviceBooker.getName())
                .email(serviceBooker.getEmail())
                .build();

        ApplicationServiceResponse applicationServiceResponse = ApplicationServiceResponse.builder()
                .description(bookedService.getDescription())
                .name(bookedService.getName())
                .cost(bookedService.getCost())
                .build();

        CreatedBookingResponse bookingResponse = CreatedBookingResponse.builder()
                .booked_service(applicationServiceResponse)
                .booker(userResponse)
                .date_time(booking.getDateTime())
                .build();
        return bookingResponse;
    }
}
