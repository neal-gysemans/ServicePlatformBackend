package com.it.serviceplatformbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplicationServiceResponse {
    private String name;
    private String description;
    private String cost;
}
