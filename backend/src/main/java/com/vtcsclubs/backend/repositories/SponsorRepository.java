package com.vtcsclubs.backend.repositories;

import com.vtcsclubs.backend.models.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
}
