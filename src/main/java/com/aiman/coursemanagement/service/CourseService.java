package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.mapper.CourseMapper;
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

    public void removePreCourse(String id) {
        final Course course = courseRepository.findById(id).orElseThrow();
        course.setPreCourse(null);
        courseRepository.save(course);
    }

    public void setPreCourse(String id, String preCourseId) {
        Course preCourse = courseRepository.findById(preCourseId).orElseThrow();
        // check cycle
        while (preCourse != null) {
            if (preCourse.getId().equals(id)) {
                throw new UnsupportedOperationException("Cyclic pre course");
            }
            preCourse = preCourse.getPreCourse();
        }
        final Course course = courseRepository.findById(id).orElseThrow();
        course.setPreCourse(courseRepository.findById(preCourseId).orElseThrow());
        courseRepository.save(course);
    }

}
