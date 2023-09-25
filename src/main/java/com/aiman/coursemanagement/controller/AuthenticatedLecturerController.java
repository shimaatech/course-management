package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.CourseDto;
import com.aiman.coursemanagement.dto.CurriculumDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.service.CourseService;
import com.aiman.coursemanagement.service.CurriculumService;
import com.aiman.coursemanagement.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lecturer")
public class AuthenticatedLecturerController {

    private final LecturerService lecturerService;
    private final CourseService courseService;
    private final CurriculumService curriculumService;

    @Autowired
    public AuthenticatedLecturerController(LecturerService lecturerService, CourseService courseService, CurriculumService curriculumService) {
        this.lecturerService = lecturerService;
        this.courseService = courseService;
        this.curriculumService = curriculumService;
    }

    @GetMapping()
    public String lecturerHome(Model model) {
        model.addAttribute("lecturer", getAuthenticatedLecturer());
        return "lecturer";
    }


    @GetMapping("/courses")
    public String getCourses(Model model) {
        List<CourseDto> courses = getAuthenticatedLecturer().getCourses();
        model.addAttribute("courses", courses);
        return "lecturer_courses";
    }


    @GetMapping("/curriculums")
    public String getCurriculums(Model model) {
        List<CurriculumDto> curriculums = curriculumService.getAllCurriculums();
        model.addAttribute("curriculums", curriculums);
        return "lecturer_curriculums";
    }

    @GetMapping("/curriculum/{id}")
    public String manageSemesters(@PathVariable("id") Long id, Model model) {
        final CurriculumDto curriculumDto = curriculumService.getCurriculumById(id);
        model.addAttribute("curriculum", curriculumDto);
        return "lecturer_curriculum";
    }


    private LecturerDto getAuthenticatedLecturer() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return lecturerService.getLecturerById(authentication.getName());
        }
        throw new SessionAuthenticationException("Lecturer is not logged in");
    }

}
