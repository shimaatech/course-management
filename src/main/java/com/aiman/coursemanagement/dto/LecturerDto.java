package com.aiman.coursemanagement.dto;

import com.aiman.coursemanagement.entity.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerDto {

    private String id;

    private String name;

    private String lastName;

    private String phone;

    private String mail;

    private List<Course> courses;

}
