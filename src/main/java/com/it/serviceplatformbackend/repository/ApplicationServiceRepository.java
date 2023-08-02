package com.it.serviceplatformbackend.repository;

import com.it.serviceplatformbackend.domain.ApplicationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationServiceRepository extends JpaRepository<ApplicationService, Long> {
    List<ApplicationService> findByUserEmail(String userEmail);

    @Query("SELECT a FROM ApplicationService a WHERE a.name = :serviceName AND a.user.email = :userEmail")
    Optional<ApplicationService> findByServiceNameAndUserEmail(String serviceName, String userEmail);

    Optional<ApplicationService> findById(Long id);

    void deleteById(Long id);
}
