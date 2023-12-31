package com.it.serviceplatformbackend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service")
public class ApplicationService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private float cost;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
