package com.aiman.coursemanagement.mapper;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.entity.Course;

public class CourseMapper {

    public static Course mapToCourse(CourseDto dto) {
        if (dto == null) {
            return null;
        }
        return Course.builder()
                .id(dto.getId())
                .name(dto.getName())
                .hours(dto.getHours())
                .preCourse(mapToCourse(dto.getPreCourse()))
                .build();
    }


    public static CourseDto mapToCourseDto(Course course) {
        if (course == null) {
            return null;
        }
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .hours(course.getHours())
                .preCourse(mapToCourseDto(course.getPreCourse()))
                .build();
    }

}
