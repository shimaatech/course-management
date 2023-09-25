package com.aiman.coursemanagement.utils;

import com.aiman.coursemanagement.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WebUtils {


    public boolean isAdmin() {
        final Authentication authentication = getAuthentication();
        return authentication.getAuthorities().contains(Role.Admin);
    }

    public boolean isLecturer() {
        final Authentication authentication = getAuthentication();
        return authentication.getAuthorities().contains(Role.Lecturer);
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated() && !getAuthentication().getName().equals("anonymousUser");
    }

    public String getUserId() {
        return getAuthentication().getName();
    }

    public boolean isUserId(String id) {
        return getAuthentication().getName().equals(id);
    }

    private static Authentication getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

}
