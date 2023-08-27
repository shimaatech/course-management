package com.aiman.coursemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semesters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    private List<Course> courses = new ArrayList<>();

}
