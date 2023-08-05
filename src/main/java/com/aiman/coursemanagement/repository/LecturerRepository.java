package com.aiman.coursemanagement.repository;

import com.aiman.coursemanagement.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, String> {
}
