package com.it.serviceplatformbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NewApplicationServiceCommand {
    private String description;
    private float cost;
    private String name;
}
