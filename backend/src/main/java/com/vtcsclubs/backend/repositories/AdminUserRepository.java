package com.vtcsclubs.backend.repositories;
import com.vtcsclubs.backend.models.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    // This method will find a user by their username.
    Optional<AdminUser> findByUsername(String username);
}
