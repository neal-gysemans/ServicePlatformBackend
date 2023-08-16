package com.it.serviceplatformbackend.service.query;

import com.it.serviceplatformbackend.domain.ApplicationService;
import com.it.serviceplatformbackend.domain.Booking;
import com.it.serviceplatformbackend.domain.User;
import com.it.serviceplatformbackend.dto.ApplicationServiceAndUserResponse;
import com.it.serviceplatformbackend.dto.BookingResponse;
import com.it.serviceplatformbackend.dto.UserResponse;
import com.it.serviceplatformbackend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingQueryService {
    private final BookingRepository bookingRepository;

    public List<BookingResponse> getPersonalBookings(String userEmail) {
        return bookingRepository.findByUserEmail(userEmail)
                .stream()
                .map(this::BookingResponseBuilder)
                .collect(Collectors.toList());
    }

    private BookingResponse BookingResponseBuilder(Booking booking) {
        UserResponse booker = UserResponseBuilder(booking.getUser());
        ApplicationServiceAndUserResponse applicationServiceAndUserResponse =
                ApplicationServiceAndUserResponseBuilder(booking.getApplicationService());

        return BookingResponse.builder()
                .id(booking.getId())
                .notes(booking.getNotes())
                .date_time(booking.getDateTime())
                .booked_service(applicationServiceAndUserResponse)
                .booker(booker)
                .build();
    }

    private ApplicationServiceAndUserResponse ApplicationServiceAndUserResponseBuilder(ApplicationService applicationService) {
        UserResponse serviceProvider = UserResponseBuilder(applicationService.getUser());

        return ApplicationServiceAndUserResponse.builder()
                .id(applicationService.getId())
                .name(applicationService.getName())
                .cost(applicationService.getCost())
                .description(applicationService.getDescription())
                .serviceProvider(serviceProvider)
                .build();
    }

    private UserResponse UserResponseBuilder(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
