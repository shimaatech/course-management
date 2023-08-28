package com.aiman.coursemanagement.dto;

import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.entity.Lecturer;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumCourseDto {

    @Id
    @GeneratedValue
    private String id;

    @ManyToOne
    private CourseDto course;

    @ManyToOne
    private LecturerDto lecturer;

}
