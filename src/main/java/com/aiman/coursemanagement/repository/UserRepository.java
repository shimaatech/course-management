package com.aiman.coursemanagement.repository;

import com.aiman.coursemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
