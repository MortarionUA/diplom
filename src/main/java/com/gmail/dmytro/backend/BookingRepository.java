package com.gmail.dmytro.backend;

import com.gmail.dmytro.backend.data.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
