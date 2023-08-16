package com.it.serviceplatformbackend.controller;

import com.it.serviceplatformbackend.auth.AuthenticationService;
import com.it.serviceplatformbackend.dto.BookingResponse;
import com.it.serviceplatformbackend.dto.CreateBookingCommand;
import com.it.serviceplatformbackend.dto.CreatedBookingResponse;
import com.it.serviceplatformbackend.service.command.BookingCommandService;
import com.it.serviceplatformbackend.service.query.BookingQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingQueryService bookingQueryService;
    private final BookingCommandService bookingCommandService;
    private final AuthenticationService authenticationService;

    @GetMapping("/personal")
    public ResponseEntity<List<BookingResponse>> getPersonalApplicationServices(HttpServletRequest request) {
        String email = authenticationService.getEmailFromToken(request);
        List<BookingResponse> personalBookings = bookingQueryService.getPersonalBookings(email);

        return new ResponseEntity<>(personalBookings, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CreatedBookingResponse> createBooking(@RequestBody CreateBookingCommand createBookingCommand, HttpServletRequest request) {
        CreatedBookingResponse newBooking = bookingCommandService.createBooking(createBookingCommand, request);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookingResponse> deleteBooking(@PathVariable long id) {
        boolean deletionResult = bookingCommandService.deleteBookingById(id);

        if (deletionResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
