package com.aiman.coursemanagement.mapper;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.entity.Course;

public class CourseMapper {

    public static Course mapToCourse(CourseDto dto) {
        return Course.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }


    public static CourseDto mapToCourse(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }

}
