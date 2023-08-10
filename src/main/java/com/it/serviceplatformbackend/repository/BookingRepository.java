package com.it.serviceplatformbackend.repository;

import com.it.serviceplatformbackend.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserEmail(String userEmail);

    List<Booking> findByApplicationService_Id(Long id);

}
