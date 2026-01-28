package com.practice.onlinedonation.Repository;

import com.practice.onlinedonation.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
