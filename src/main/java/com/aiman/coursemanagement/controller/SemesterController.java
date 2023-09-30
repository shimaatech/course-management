package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.dto.SemesterDto;
import com.aiman.coursemanagement.entity.Lecturer;
import com.aiman.coursemanagement.entity.Semester;
import com.aiman.coursemanagement.service.CourseService;
import com.aiman.coursemanagement.service.LecturerService;
import com.aiman.coursemanagement.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("semesters")
public class SemesterController {

    private final SemesterService semesterService;
    private final CourseService courseService;
    private final LecturerService lecturerService;

    @Autowired
    public SemesterController(SemesterService semesterService, CourseService courseService, LecturerService lecturerService) {
        this.semesterService = semesterService;
        this.courseService = courseService;
        this.lecturerService = lecturerService;
    }



    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable("id") Long semesterId, @RequestParam("curriculumId") Long curriculumId, Model model) {
        final SemesterDto semesterDto = semesterService.getSemesterById(semesterId);
        model.addAttribute("curriculumId", curriculumId);
        model.addAttribute("editMode", true);
        return newOrEditSemester(model, semesterDto);
    }

    @GetMapping("/new")
    public String newSemester(@RequestParam("curriculumId") Long curriculumId, Model model) {
        model.addAttribute("curriculumId", curriculumId);
        return newOrEditSemester(model, new SemesterDto());
    }


    private String newOrEditSemester(Model model, SemesterDto semesterDto) {
        model.addAttribute("newSemester", semesterDto);
        if (!model.containsAttribute("editMode")) {
            model.addAttribute("editMode", false);
        }
        return "add_edit_semester";
    }



    @PostMapping()
    public String createSemester(SemesterDto semesterDto, @RequestParam(name = "curriculumId", required = true) Long curriculumId) {
        semesterService.createSemester(semesterDto, curriculumId);
        return "redirect:/curriculums/" + curriculumId + "/manage-semesters";
    }



    @PutMapping()
    public String updateSemester(SemesterDto semesterDto, @RequestParam(name = "curriculumId", required = true) Long curriculumId) {
        semesterService.updateSemester(semesterDto);
        return "redirect:/curriculums/" + curriculumId + "/manage-semesters";
    }


    @GetMapping("/{id}/add-course")
    public String addCourse(
            @PathVariable("id") Long semesterId,
            @RequestParam("curriculumId") Long curriculumId,
            @RequestParam(value = "courseId", required = false) String courseId, Model model) {

        final SemesterDto semester = semesterService.getSemesterById(semesterId);
        final List<CourseDto> courses = courseService.getAllCourses();

        model.addAttribute("semester", semester);
        model.addAttribute("courses", courses);
        model.addAttribute("curriculumId", curriculumId);


        if (courseId != null) {
            final CourseDto selectedCourse = courseService.getCourseById(courseId);
            final List<LecturerDto> courseLecturers = lecturerService.getCourseLecturers(courseId);

            model.addAttribute("selectedCourse", selectedCourse);
            model.addAttribute("lecturers", courseLecturers);

        }

        return "add_semester_course";
    }

    @PostMapping("/{id}/add-course")
    public String addCourse(
            @PathVariable("id") Long semesterId,
            @RequestParam("courseId") String courseId,
            @RequestParam("lecturerId") String lecturerId,
            @RequestParam("curriculumId") String curriculumId
            ) {

        semesterService.addCourse(semesterId, courseId, lecturerId);
        return "redirect:/curriculums/" + curriculumId + "/manage-semesters";
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteSemester(@PathVariable("id") Long semester) {
        semesterService.deleteSemester(semester);
    }


    @DeleteMapping("/{id}/delete-course")
    @ResponseBody
    public void deleteSemesterCourse(@PathVariable("id") Long semesterId, @RequestParam("courseId") String courseId) {
        semesterService.deleteCourse(semesterId, courseId);
    }

}
