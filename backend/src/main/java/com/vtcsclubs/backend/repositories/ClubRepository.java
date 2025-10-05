package com.vtcsclubs.backend.repositories;

import com.vtcsclubs.backend.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
