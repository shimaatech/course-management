package com.aiman.coursemanagement.repository;

import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer, String> {

    @Query("SELECT l FROM Lecturer l JOIN l.courses c WHERE c.id = :courseId")
    List<Lecturer> getLecturersByCourseId(String courseId);
}
