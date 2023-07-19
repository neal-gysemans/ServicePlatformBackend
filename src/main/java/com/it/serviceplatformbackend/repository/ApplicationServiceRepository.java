package com.it.serviceplatformbackend.repository;

import com.it.serviceplatformbackend.domain.ApplicationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationServiceRepository extends JpaRepository<ApplicationService, Long> {
}
