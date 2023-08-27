package com.aiman.coursemanagement.mapper;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.SemesterDto;
import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.entity.Semester;

public class SemesterMapper {

    public static Semester mapToSemester(SemesterDto dto) {
        if (dto == null) {
            return null;
        }
        return Semester.builder()
                .id(dto.getId())
                .name(dto.getName())
                .courses(dto.getCourses().stream().map(CourseMapper::mapToCourse).toList())
                .build();
    }


    public static SemesterDto mapToSemesterDto(Semester semester) {
        if (semester == null) {
            return null;
        }
        return SemesterDto.builder()
                .id(semester.getId())
                .name(semester.getName())
                .courses(semester.getCourses().stream().map(CourseMapper::mapToCourseDto).toList())
                .build();
    }

}
