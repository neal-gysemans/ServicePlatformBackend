package com.it.serviceplatformbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateApplicationServiceCommand {
    private long id;
    private String description;
    private float cost;
    private String name;
}
