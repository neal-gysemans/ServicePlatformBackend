package com.it.serviceplatformbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "service")
public class ApplicationService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String cost;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
