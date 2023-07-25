package com.it.serviceplatformbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String email;

    private String name;
}
