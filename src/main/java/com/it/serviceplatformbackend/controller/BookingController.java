package com.it.serviceplatformbackend.controller;

import com.it.serviceplatformbackend.auth.AuthenticationService;
import com.it.serviceplatformbackend.dto.BookingResponse;
import com.it.serviceplatformbackend.service.query.BookingQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingQueryService bookingQueryService;
    private final AuthenticationService authenticationService;

    @GetMapping("/personal")
    public ResponseEntity<List<BookingResponse>> getPersonalApplicationServices(HttpServletRequest request) {
        // Get the email from the JWT token in the request
        String email = authenticationService.getEmailFromToken(request);
        System.out.println(email);
        // Call the service method to get the user's personal services
        List<BookingResponse> personalBookings = bookingQueryService.getPersonalBookings(email);

        return new ResponseEntity<>(personalBookings, HttpStatus.OK);
    }
}
