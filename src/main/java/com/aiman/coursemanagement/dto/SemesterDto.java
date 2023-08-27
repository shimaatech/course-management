package com.aiman.coursemanagement.dto;


import com.aiman.coursemanagement.entity.Course;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemesterDto {

    private Long id;

    private String name;

    private List<CourseDto> courses = new ArrayList<>();

}
