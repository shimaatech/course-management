package com.aiman.coursemanagement.repository;

import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
}
