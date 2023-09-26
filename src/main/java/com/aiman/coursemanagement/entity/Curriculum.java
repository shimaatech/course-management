package com.aiman.coursemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "curriculums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curriculum {

    @Id
    @GeneratedValue
    private Long id;

    private Integer startYear;

    private Integer endYear;

    private String hebrewStartYear;

    private String hebrewEndYear;

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    private List<Semester> semesters = new ArrayList<>();

}
