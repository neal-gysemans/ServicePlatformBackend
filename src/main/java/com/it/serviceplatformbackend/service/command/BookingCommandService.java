package com.it.serviceplatformbackend.service.command;

import com.it.serviceplatformbackend.auth.AuthenticationService;
import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.domain.Booking;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceResponse;
import com.it.serviceplatformbackend.dto.CreateBookingCommand;
import com.it.serviceplatformbackend.dto.CreatedBookingResponse;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
import com.it.serviceplatformbackend.repository.BookingRepository;
import com.it.serviceplatformbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookingCommandService {
    private final BookingRepository bookingRepository;
    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final ApplicationServiceRepository applicationServiceRepository;


    public CreatedBookingResponse createBooking(CreateBookingCommand createBookingCommand, HttpServletRequest request) {
        User serviceBooker = userRepository.findByEmail(authenticationService.getEmailFromToken(request))
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        ApplicationService bookedService = applicationServiceRepository.findById(createBookingCommand.getBooked_service_id())
                .orElseThrow(() -> new EntityNotFoundException("Service not found."));

        Booking booking = Booking.builder()
                .applicationService(bookedService)
                .user(serviceBooker)
                .dateTime(createBookingCommand.getDate_time())
                .notes(createBookingCommand.getNotes())
                .build();

        bookingRepository.save(booking);

        CreatedBookingResponse bookingResponse = getCreatedBookingResponse(booking);
        return bookingResponse;
    }

    private static CreatedBookingResponse getCreatedBookingResponse(Booking booking) {
        // Building the response
        UserResponse userResponse = UserResponse.builder()
                .name(booking.getUser().getName())
                .email(booking.getUser().getEmail())
                .build();

        ApplicationServiceResponse applicationServiceResponse = ApplicationServiceResponse.builder()
                .description(booking.getApplicationService().getDescription())
                .name(booking.getApplicationService().getName())
                .cost(booking.getApplicationService().getCost())
                .build();

        CreatedBookingResponse bookingResponse = CreatedBookingResponse.builder()
                .booked_service(applicationServiceResponse)
                .booker(userResponse)
                .date_time(booking.getDateTime())
                .notes(booking.getNotes())
                .build();
        return bookingResponse;
    }
}
