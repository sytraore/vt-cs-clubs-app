package com.vtcsclubs.backend.repositories;

import com.vtcsclubs.backend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
