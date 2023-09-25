package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.service.CourseService;
import com.aiman.coursemanagement.service.LecturerService;
import com.aiman.coursemanagement.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;
    private final CourseService courseService;

    @Autowired
    public LecturerController(LecturerService lecturerService, CourseService courseService) {
        this.lecturerService = lecturerService;
        this.courseService = courseService;
    }

    @GetMapping()
    public String getLecturers(Model model) {
        List<LecturerDto> lecturers = lecturerService.getAllLecturers();
        model.addAttribute("lecturers", lecturers);
        return "lecturers";
    }


    @PostMapping()
    public String createLecturer(LecturerDto lecturerDto, @RequestParam(name = "coursesIds", required = false) List<String> coursesIds) {
        lecturerService.createLecturer(lecturerDto, coursesIds);
        return "redirect:/lecturers";
    }


    @GetMapping("/new")
    public String newLecturerForm(Model model) {
        final LecturerDto lecturerDto = new LecturerDto();
        lecturerDto.setPassword(GeneralUtils.generateRandomPassword(10));
        model.addAttribute("lecturer", lecturerDto);
        model.addAttribute("allCourses", courseService.getAllCourses());
        model.addAttribute("selectedCourses", Collections.emptyList());
        return "add_lecturer";
    }


    @GetMapping("/{id}/manage-courses")
    public String manageCourses(@PathVariable("id") String lecturerId, Model model) {
        final LecturerDto lecturerDto = lecturerService.getLecturerById(lecturerId);
        List<Course> lecturerCourses = lecturerDto.getCourses();
        List<String> lecturerCoursesIds = lecturerCourses.stream().map(Course::getId).toList();
        model.addAttribute("allCourses", courseService.getAllCourses().stream().filter(course -> !lecturerCoursesIds.contains(course.getId())).toList());
        model.addAttribute("lecturer", lecturerDto);
        model.addAttribute("selectedCourses", lecturerCourses);
        return "add_remove_lecturer_courses";
    }


    @PostMapping("/{id}/set-courses")
    @ResponseBody
    public void setLecturerCourses(@PathVariable("id") String lecturerId, @RequestParam("courses") List<String> coursesIds) {
        lecturerService.setCourses(lecturerId, coursesIds);
    }

}
