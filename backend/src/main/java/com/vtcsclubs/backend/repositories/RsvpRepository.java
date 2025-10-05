package com.vtcsclubs.backend.repositories;

import com.vtcsclubs.backend.models.Rsvp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RsvpRepository extends JpaRepository<Rsvp, Long> {
}
