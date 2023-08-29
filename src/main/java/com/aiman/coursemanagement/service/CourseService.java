package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.entity.Lecturer;
import com.aiman.coursemanagement.mapper.CourseMapper;
import com.aiman.coursemanagement.repository.CourseRepository;
import io.micrometer.common.util.StringUtils;
import org.codehaus.groovy.util.StringUtil;
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

    public void createCourse(CourseDto courseDto, String preCourseId) {
        final Course course = CourseMapper.mapToCourse(courseDto);
        if (!StringUtils.isBlank(preCourseId)) {
            final Course preCourse = courseRepository.findById(preCourseId).orElseThrow();
            course.setPreCourse(preCourse);
        }
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
        checkPreCourseCycle(id, preCourseId);
        final Course course = courseRepository.findById(id).orElseThrow();
        course.setPreCourse(courseRepository.findById(preCourseId).orElseThrow());
        courseRepository.save(course);
    }

    private void checkPreCourseCycle(String courseId, String preCourseId) {
        Course preCourse = courseRepository.findById(preCourseId).orElseThrow();
        while (preCourse != null) {
            if (preCourse.getId().equals(courseId)) {
                throw new RuntimeException("Cyclic pre course");
            }
            preCourse = preCourse.getPreCourse();
        }
    }

}
