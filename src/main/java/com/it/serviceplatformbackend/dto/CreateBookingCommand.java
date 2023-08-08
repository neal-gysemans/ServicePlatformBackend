package com.it.serviceplatformbackend.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateBookingCommand {
    private long booked_service_id;
    private String notes;
    private LocalDateTime date_time;
}
