package com.aiman.coursemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer {

    @Id
    private String id;

    private String name;

    private String lastName;

    private String phone;

    private String mail;

    private String cvPath;

    @ManyToMany()
    private List<Course> courses = new ArrayList<>();
}
