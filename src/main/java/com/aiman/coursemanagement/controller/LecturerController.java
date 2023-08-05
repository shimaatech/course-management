package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Lecturer;
import com.aiman.coursemanagement.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping()
    public String getLecturers(Model model) {
        List<LecturerDto> lecturers = lecturerService.getAllLecturers();
        model.addAttribute("lecturers", lecturers);
        return "lecturers";
    }


    @PostMapping()
    public String createLecturer(LecturerDto lecturerDto) {
        lecturerService.createLecturer(lecturerDto);
        return "redirect:/lecturers";
    }


    @GetMapping("/new")
    public String newLecturerForm(Model model) {
        final LecturerDto lecturerDto = new LecturerDto();
        model.addAttribute("lecturer", lecturerDto);
        return "add_lecturer";
    }

}
