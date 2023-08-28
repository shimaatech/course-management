package com.aiman.coursemanagement.mapper;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.CurriculumCourseDto;
import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.entity.CurriculumCourse;
import com.aiman.coursemanagement.entity.Lecturer;

public class CurriculumCourseMapper {

    public static CurriculumCourse mapToCourse(CurriculumCourseDto dto) {
        if (dto == null) {
            return null;
        }
        return CurriculumCourse.builder()
                .id(dto.getId())
                .lecturer(LecturerMapper.mapToLecturer(dto.getLecturer()))
                .course(CourseMapper.mapToCourse(dto.getCourse()))
                .build();
    }


    public static CurriculumCourseDto mapToCourseDto(CurriculumCourse course) {
        if (course == null) {
            return null;
        }
        return CurriculumCourseDto.builder()
                .id(course.getId())
                .lecturer(LecturerMapper.mapToLecturerDto(course.getLecturer()))
                .course(CourseMapper.mapToCourseDto(course.getCourse()))
                .build();
    }

}
