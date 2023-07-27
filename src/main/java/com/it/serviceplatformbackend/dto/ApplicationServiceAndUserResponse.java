package com.it.serviceplatformbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplicationServiceAndUserResponse {
    private long id;
    private String name;
    private String description;
    private String cost;
    private UserResponse serviceProvider;

}
