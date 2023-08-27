package com.aiman.coursemanagement.repository;

import com.aiman.coursemanagement.entity.Curriculum;
import com.aiman.coursemanagement.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
}
