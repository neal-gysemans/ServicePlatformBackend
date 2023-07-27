package com.it.serviceplatformbackend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class BookingResponse {
    private long id;
    private String notes;
    private LocalDateTime date_time;
    private UserResponse booker;
    private ApplicationServiceAndUserResponse booked_service;

}
