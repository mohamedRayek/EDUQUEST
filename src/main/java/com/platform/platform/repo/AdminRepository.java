package com.platform.platform.repo;

import com.platform.platform.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);
    boolean existsByEmail(String email);
}
