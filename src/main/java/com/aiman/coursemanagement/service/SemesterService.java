package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.SemesterDto;
import com.aiman.coursemanagement.entity.*;
import com.aiman.coursemanagement.mapper.SemesterMapper;
import com.aiman.coursemanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;

    private final CurriculumRepository curriculumRepository;

    private final CurriculumCourseRepository curriculumCourseRepository;

    @Autowired
    public SemesterService(SemesterRepository semesterRepository, CourseRepository courseRepository, LecturerRepository lecturerRepository, CurriculumRepository curriculumRepository, CurriculumCourseRepository curriculumCourseRepository) {
        this.semesterRepository = semesterRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.curriculumRepository = curriculumRepository;
        this.curriculumCourseRepository = curriculumCourseRepository;
    }


    public void createSemester(SemesterDto semesterDto, Long curriculumId) {
        final Semester semester = SemesterMapper.mapToSemester(semesterDto);
        final Curriculum curriculum = curriculumRepository.findById(curriculumId).orElseThrow();
        List<Semester> curriculumSemesters = new ArrayList<>(curriculum.getSemesters());
        curriculumSemesters.add(semester);
        curriculum.setSemesters(curriculumSemesters);
        semester.setCurriculum(curriculum);
        semesterRepository.save(semester);
    }

    public void addCourse(Long semesterId, String courseId, String lecturerId) {
        final Semester semester = semesterRepository.findById(semesterId).orElseThrow();
        final Course course = courseRepository.findById(courseId).orElseThrow();
        final Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow();
        final List<CurriculumCourse> courses = new ArrayList<>(semester.getCourses());
        final CurriculumCourse curriculumCourse = new CurriculumCourse();
        curriculumCourse.setCourse(course);
        curriculumCourse.setLecturer(lecturer);
        curriculumCourseRepository.save(curriculumCourse);
        courses.add(curriculumCourse);
        semester.setCourses(courses);
        semesterRepository.save(semester);
    }

    public SemesterDto getSemesterById(Long id) {
        return SemesterMapper.mapToSemesterDto(semesterRepository.findById(id).orElseThrow());
    }

    public void deleteSemester(Long id) {
        semesterRepository.deleteById(id);
    }

    public void deleteCourse(Long semesterId, String courseId) {
        final Semester semester = semesterRepository.findById(semesterId).orElseThrow();
        semester.getCourses().removeIf(course -> course.getCourse().getId().equals(courseId));
        semesterRepository.save(semester);
    }
}
