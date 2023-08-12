package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public String getCourses(Model model) {
        List<CourseDto> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }


    @PostMapping()
    public String createCourse(CourseDto courseDto) {
        courseService.createCourse(courseDto);
        return "redirect:/courses";
    }


    @GetMapping("/new")
    public String newCourseForm(Model model) {
        final CourseDto courseDto = new CourseDto();
        model.addAttribute("course", courseDto);
        return "add_course";
    }

    @GetMapping("/pre-courses/{id}")
    public String managePreCourses(@PathVariable String id, Model model) {
        final CourseDto courseDto = courseService.getCourseById(id);
        final List<CourseDto> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("course", courseDto);
        return "pre-courses";
    }


    @GetMapping("/pre-courses/delete/{id}")
    public String deletePreCourse(@PathVariable String id, @RequestParam String preCourseId, Model model) {
        courseService.deletePreCourse(id, preCourseId);
        return "redirect:/courses/pre-courses/" + id;
    }


    @GetMapping("/pre-courses/add/{id}")
    public String addPreCourse(@PathVariable String id, @RequestParam String preCourseId, Model model) {
        courseService.addPreCourse(id, preCourseId);
        return "redirect:/courses/pre-courses/" + id;
    }


}
