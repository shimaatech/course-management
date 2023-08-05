package com.aiman.coursemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    private String mail;

    @ManyToMany()
    private List<Course> courses;
}
