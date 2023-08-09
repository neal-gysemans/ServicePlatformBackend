package com.it.serviceplatformbackend.dto;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class UserStatusResponse {
    private long id;
    private String email;
    private String name;
    private boolean active;
}
