package com.aiman.coursemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curriculum {

    @Id
    private Long id;

    private Integer startYear;

    private Integer endYear;

    private String hebrewStartYear;

    private String hebrewEndYear;

    @OneToMany
    private List<Semester> semesters;

}
