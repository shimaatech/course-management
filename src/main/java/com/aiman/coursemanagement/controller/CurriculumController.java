package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.CurriculumDto;
import com.aiman.coursemanagement.service.CourseService;
import com.aiman.coursemanagement.service.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("curriculums")
public class CurriculumController {

    private final CurriculumService curriculumService;

    @Autowired
    public CurriculumController(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    @GetMapping()
    public String getCurriculums(Model model) {
        List<CurriculumDto> curriculums = curriculumService.getAllCurriculums();
        model.addAttribute("curriculums", curriculums);
        return "curriculums";
    }


    @PostMapping()
    public String createCurriculum(CurriculumDto curriculumDto) {
        curriculumService.createCurriculum(curriculumDto);
        return "redirect:/curriculums";
    }


    @GetMapping("/new")
    public String newCourseForm(Model model) {
        final CurriculumDto curriculumDto = new CurriculumDto();
        model.addAttribute("newCurriculum", curriculumDto);
        return "create_curriculum";
    }


    @GetMapping("/{id}/manage-semesters")
    public String manageSemesters(@PathVariable("id") Long id, Model model) {
        final CurriculumDto curriculumDto = curriculumService.getCurriculumById(id);
        model.addAttribute("curriculum", curriculumDto);
        return "curriculum_management";
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteCurriculum(@PathVariable("id") Long curriculumId) {
        curriculumService.deleteCurriculum(curriculumId);
    }

}
