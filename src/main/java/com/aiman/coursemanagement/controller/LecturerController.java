package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.service.CourseService;
import com.aiman.coursemanagement.service.LecturerService;
import com.aiman.coursemanagement.service.UploadService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;
    private final CourseService courseService;

    private final UploadService uploadService;

    @Autowired
    public LecturerController(LecturerService lecturerService, CourseService courseService, UploadService uploadService) {
        this.lecturerService = lecturerService;
        this.courseService = courseService;
        this.uploadService = uploadService;
    }

    @GetMapping()
    public String getLecturers(Model model) {
        List<LecturerDto> lecturers = lecturerService.getAllLecturers();
        model.addAttribute("lecturers", lecturers);
        return "lecturers";
    }


    @PostMapping()
    public String createLecturer(LecturerDto lecturerDto, @RequestParam(name = "coursesIds", required = false) List<String> coursesIds, @RequestParam(value = "cv", required = false) MultipartFile cvFile) throws IOException {
        uploadLecturerCv(lecturerDto, cvFile);
        lecturerService.createLecturer(lecturerDto, coursesIds);
        return "redirect:/lecturers";
    }

    @PutMapping()
    public String updateLecturer(LecturerDto lecturerDto, @RequestParam(value = "cv", required = false) MultipartFile cvFile) throws IOException {
        uploadLecturerCv(lecturerDto, cvFile);
        lecturerService.updateLecturer(lecturerDto);
        return "redirect:/lecturers";
    }

    @GetMapping("/cv")
    public ResponseEntity<Resource> downloadFile(@RequestParam("path") String path) throws IOException {
        return uploadService.downloadFile(path);
    }


    private void uploadLecturerCv(LecturerDto lecturerDto, MultipartFile file) throws IOException {
        if (file != null && !StringUtils.isEmpty(file.getName())) {
            String cvPath = uploadService.uploadFile(file, "lecturers/cv", "cv/" + UUID.randomUUID());
            lecturerDto.setCvPath(cvPath);
        }
    }


    @GetMapping("/{id}/edit")
    public String editLecturer(@PathVariable("id") String lecturerId, Model model) {
        final LecturerDto lecturerDto = lecturerService.getLecturerById(lecturerId);
        model.addAttribute("editMode", true);
        return newOrEditLecturer(model, lecturerDto);
    }


    @GetMapping("/new")
    public String newLecturerForm(Model model) {
        final LecturerDto lecturerDto = new LecturerDto();
        return newOrEditLecturer(model, lecturerDto);
    }

    private String newOrEditLecturer(Model model, LecturerDto lecturerDto) {
        model.addAttribute("lecturer", lecturerDto);
        model.addAttribute("allCourses", courseService.getAllCourses());
        model.addAttribute("selectedCourses", Collections.emptyList());
        if (!model.containsAttribute("editMode")) {
            model.addAttribute("editMode", false);
        }
        return "add_edit_lecturer";
    }


    @GetMapping("/{id}/manage-courses")
    public String manageCourses(@PathVariable("id") String lecturerId, Model model) {
        final LecturerDto lecturerDto = lecturerService.getLecturerById(lecturerId);
        List<CourseDto> lecturerCourses = lecturerDto.getCourses();
        List<String> lecturerCoursesIds = lecturerCourses.stream().map(CourseDto::getId).toList();
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

    @PostMapping("/{id}/new-password")
    @ResponseBody
    public String generateNewPassword(@PathVariable("id") String lecturerId) {
        return lecturerService.updateLecturerPassword(lecturerId);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteLecturer(@PathVariable("id") String lecturerId) {
        lecturerService.deleteLecturer(lecturerId);
    }

}
