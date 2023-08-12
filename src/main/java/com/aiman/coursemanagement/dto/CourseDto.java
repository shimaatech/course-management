package com.aiman.coursemanagement.dto;


import com.aiman.coursemanagement.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private String id;

    private String name;

    private Integer hours;

    private List<Course> preCourses;

}
