package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
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
    public String createCourse(CourseDto courseDto, @RequestParam(name = "preCourseId", required = false) String preCourseId) {
        courseService.createCourse(courseDto, preCourseId);
        return "redirect:/courses";
    }


    @PutMapping()
    public String updateCourse(CourseDto courseDto) {
        courseService.updateCourse(courseDto);
        return "redirect:/courses";
    }


    @GetMapping("/new")
    public String newCourseForm(Model model) {
        return newOrEditCourse(model, new CourseDto());
    }


    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable("id") String courseId, Model model) {
        final CourseDto courseDto = courseService.getCourseById(courseId);
        model.addAttribute("editMode", true);
        return newOrEditCourse(model, courseDto);
    }


    private String newOrEditCourse(Model model, CourseDto courseDto) {
        model.addAttribute("newCourse", courseDto);
        model.addAttribute("courses", courseService.getAllCourses());
        if (!model.containsAttribute("editMode")) {
            model.addAttribute("editMode", false);
        }
        return "add_edit_course";
    }


    @PostMapping("/{id}/remove-pre-course")
    @ResponseBody
    public ResponseEntity removePreCourse(@PathVariable String id, Model model) {
        courseService.removePreCourse(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/{id}/set-pre-course")
    @ResponseBody
    public ResponseEntity setPreCourse(@PathVariable String id, @RequestParam String preCourseId, Model model) {
        try {
            courseService.setPreCourse(id, preCourseId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteLecturer(@PathVariable("id") String courseId) {
        courseService.deleteCourse(courseId);
    }

}
