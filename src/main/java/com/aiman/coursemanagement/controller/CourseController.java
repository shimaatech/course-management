package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
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


    @PostMapping("/{id}/remove-pre-course")
    @ResponseBody
    public Boolean removePreCourse(@PathVariable String id, Model model) {
        courseService.removePreCourse(id);
        return Boolean.TRUE;
    }


    @PostMapping("/{id}/set-pre-course")
    @ResponseBody
    public Boolean setPreCourse(@PathVariable String id, @RequestParam String preCourseId, Model model) {
        courseService.setPreCourse(id, preCourseId);
        return Boolean.TRUE;
    }


}
