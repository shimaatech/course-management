package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.entity.Lecturer;
import com.aiman.coursemanagement.entity.User;
import com.aiman.coursemanagement.mapper.LecturerMapper;
import com.aiman.coursemanagement.model.Role;
import com.aiman.coursemanagement.repository.CourseRepository;
import com.aiman.coursemanagement.repository.LecturerRepository;
import com.aiman.coursemanagement.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;

    private final UserService userService;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository, CourseRepository courseRepository, UserService userService) {
        this.lecturerRepository = lecturerRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<LecturerDto> getAllLecturers() {
        return lecturerRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(LecturerMapper::mapToLecturerDto).toList();
    }

    public void createLecturer(LecturerDto lecturerDto, List<String> coursesIds) {
        final Lecturer lecturer = LecturerMapper.mapToLecturer(lecturerDto);
        lecturerDto.setPassword(generatePassword());
        setCourses(lecturer, coursesIds);
        lecturerRepository.save(lecturer);
        userService.createUser(lecturerDto.getId(), lecturerDto.getPassword(), Role.Lecturer);
    }

    public LecturerDto getLecturerById(String lecturerId) {
        final Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow();
        return LecturerMapper.mapToLecturerDto(lecturer);
    }

    public void setCourses(String lecturerId, List<String> coursesIds) {
        final Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow();
        setCourses(lecturer, coursesIds);
        lecturerRepository.save(lecturer);
    }

    private void setCourses(Lecturer lecturer, List<String> coursesIds) {
        final List<Course> courses = courseRepository.findAllById(coursesIds);
        lecturer.setCourses(courses);
    }

    public List<LecturerDto> getCourseLecturers(String courseId) {
        return lecturerRepository.getLecturersByCourseId(courseId).stream().map(LecturerMapper::mapToLecturerDto).toList();
    }

    public String updateLecturerPassword(String lecturerId) {
        final String password = generatePassword();
        userService.updateUserPassword(lecturerId, password);
        return password;
    }

    public void deleteLecturer(String lecturerId) {
        lecturerRepository.deleteById(lecturerId);
    }

    public void updateLecturer(LecturerDto lecturerDto) {
        final Lecturer lecturerToUpdate = lecturerRepository.findById(lecturerDto.getId()).orElseThrow();
        lecturerToUpdate.setName(lecturerDto.getName());
        lecturerToUpdate.setMail(lecturerDto.getMail());
        lecturerToUpdate.setLastName(lecturerDto.getLastName());
        lecturerToUpdate.setPhone(lecturerDto.getPhone());
        if (!StringUtils.isEmpty(lecturerDto.getCvPath())) {
            lecturerToUpdate.setCvPath(lecturerDto.getCvPath());
        }
        lecturerRepository.save(lecturerToUpdate);
    }

    private static String generatePassword() {
        return GeneralUtils.generateRandomPassword(10);
    }
}
