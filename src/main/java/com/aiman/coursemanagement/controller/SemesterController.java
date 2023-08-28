package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.SemesterDto;
import com.aiman.coursemanagement.service.CourseService;
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

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }



    @GetMapping("/new")
    public String newSemester(@RequestParam("curriculumId") Long curriculumId, Model model) {
        final SemesterDto semesterDto = new SemesterDto();
        model.addAttribute("newSemester", semesterDto);
        model.addAttribute("curriculumId", curriculumId);
        return "add_semester";
    }

    @PostMapping()
    public String createSemester(SemesterDto semesterDto, @RequestParam(name = "curriculumId", required = true) Long curriculumId) {
        semesterService.createSemester(semesterDto, curriculumId);
        return "redirect:/curriculums/" + curriculumId + "/manage-semesters";
    }


}
