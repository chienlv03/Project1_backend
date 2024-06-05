package com.example.projecttest.Repository;

import com.example.projecttest.Entity.Enum.ERole;
import com.example.projecttest.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
