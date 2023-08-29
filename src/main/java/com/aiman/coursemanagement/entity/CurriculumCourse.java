package com.aiman.coursemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curriculum_courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurriculumCourse {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Lecturer lecturer;

}
