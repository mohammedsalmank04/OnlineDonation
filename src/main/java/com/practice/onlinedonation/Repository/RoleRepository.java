package com.practice.onlinedonation.Repository;

import com.practice.onlinedonation.Model.AppRole;
import com.practice.onlinedonation.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
