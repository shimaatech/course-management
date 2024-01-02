package com.aiman.coursemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    private String id;

    private String name;

    private Integer hours;

    private String syllabusPath;

    @ManyToOne
    private Course preCourse;

}
