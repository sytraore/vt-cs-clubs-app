package com.vtcsclubs.backend.repositories;

import com.vtcsclubs.backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
