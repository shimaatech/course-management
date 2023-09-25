package com.aiman.coursemanagement.controller;

import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.service.CourseService;
import com.aiman.coursemanagement.service.LecturerService;
import com.aiman.coursemanagement.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/lecturer")
public class AuthenticatedLecturerController {

    private final LecturerService lecturerService;
    private final CourseService courseService;

    @Autowired
    public AuthenticatedLecturerController(LecturerService lecturerService, CourseService courseService) {
        this.lecturerService = lecturerService;
        this.courseService = courseService;
    }

    @GetMapping("")
    public String lecturerHome(Model model) {
        model.addAttribute("lecturer", getAuthenticatedLecturer());
        return "lecturer";
    }

    private LecturerDto getAuthenticatedLecturer() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return lecturerService.getLecturerById(authentication.getName());
        }
        throw new SessionAuthenticationException("Lecturer is not logged in");
    }

}
