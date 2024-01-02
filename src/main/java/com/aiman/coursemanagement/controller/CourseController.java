package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.service.CourseService;
import com.aiman.coursemanagement.service.UploadService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("courses")
public class CourseController {

    private final CourseService courseService;

    private final UploadService uploadService;

    @Autowired
    public CourseController(CourseService courseService, UploadService uploadService) {
        this.courseService = courseService;
        this.uploadService = uploadService;
    }

    @GetMapping()
    public String getCourses(Model model) {
        List<CourseDto> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }


    @PostMapping()
    public String createCourse(CourseDto courseDto, @RequestParam(name = "preCourseId", required = false) String preCourseId, @RequestParam(value = "syllabus", required = false) MultipartFile syllabusFile) throws IOException {
        uploadCourseSyllabus(courseDto, syllabusFile);
        courseService.createCourse(courseDto, preCourseId);
        return "redirect:/courses";
    }


    @PutMapping()
    public String updateCourse(CourseDto courseDto, @RequestParam(value = "syllabus", required = false) MultipartFile syllabusFile) throws IOException {
        uploadCourseSyllabus(courseDto, syllabusFile);
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


    @GetMapping("/syllabus")
    public ResponseEntity<Resource> downloadFile(@RequestParam("path") String path) throws IOException {
        return uploadService.downloadFile(path);
    }
    private void uploadCourseSyllabus(CourseDto courseDto, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String cvPath = uploadService.uploadFile(file, "courses/syllabus", "syllabus/" + UUID.randomUUID());
            courseDto.setSyllabusPath(cvPath);
        }
    }


}
