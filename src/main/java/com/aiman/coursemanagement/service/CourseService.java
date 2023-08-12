package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.entity.Lecturer;
import com.aiman.coursemanagement.mapper.CourseMapper;
import com.aiman.coursemanagement.mapper.LecturerMapper;
import com.aiman.coursemanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream().map(CourseMapper::mapToCourseDto).toList();
    }

    public void createCourse(CourseDto courseDto) {
        final Course course = CourseMapper.mapToCourse(courseDto);
        courseRepository.save(course);
    }

    public CourseDto getCourseById(String courseId) {
        return CourseMapper.mapToCourseDto(courseRepository.findById(courseId).orElseThrow());
    }

    public void deletePreCourse(String id, String preCourseId) {
        final Course course = courseRepository.findById(id).orElseThrow();
        course.getPreCourses().removeIf(c -> c.getId().equals(preCourseId));
        courseRepository.save(course);
    }

    public void addPreCourse(String id, String preCourseId) {
        final Course course = courseRepository.findById(id).orElseThrow();
        course.getPreCourses().add(courseRepository.findById(preCourseId).orElseThrow());
        courseRepository.save(course);
    }
}
