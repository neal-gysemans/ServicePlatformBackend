package com.it.serviceplatformbackend.repository;

import com.it.serviceplatformbackend.domain.ApplicationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationServiceRepository extends JpaRepository<ApplicationService, Long> {
    List<ApplicationService> findByUserEmail(String userEmail);

    void deleteById(Long id);
}
