package com.aiman.coursemanagement.mapper;

import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Lecturer;

public class LecturerMapper {

    public static Lecturer mapToLecturer(LecturerDto dto) {
        return Lecturer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .mail(dto.getMail())
                .courses(dto.getCourses())
                .build();
    }

    public static LecturerDto mapToLecturerDto(Lecturer lecturer) {
        return LecturerDto.builder()
                .id(lecturer.getId())
                .name(lecturer.getName())
                .lastName(lecturer.getLastName())
                .phone(lecturer.getPhone())
                .mail(lecturer.getMail())
                .courses(lecturer.getCourses())
                .build();
    }
}
