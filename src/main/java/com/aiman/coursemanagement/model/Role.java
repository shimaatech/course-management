package com.aiman.coursemanagement.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    Admin,
    Lecturer;

    @Override
    public String getAuthority() {
        return name();
    }
}
