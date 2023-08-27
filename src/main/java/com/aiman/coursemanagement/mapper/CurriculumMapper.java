package com.aiman.coursemanagement.mapper;

import com.aiman.coursemanagement.dto.CurriculumDto;
import com.aiman.coursemanagement.dto.SemesterDto;
import com.aiman.coursemanagement.entity.Curriculum;
import com.aiman.coursemanagement.entity.Semester;

public class CurriculumMapper {

    public static Curriculum mapToCurriculum(CurriculumDto dto) {
        if (dto == null) {
            return null;
        }
        return Curriculum.builder()
                .id(dto.getId())
                .startYear(dto.getStartYear())
                .endYear(dto.getEndYear())
                .hebrewStartYear(dto.getHebrewStartYear())
                .hebrewEndYear(dto.getHebrewEndYear())
                .semesters(dto.getSemesters().stream().map(SemesterMapper::mapToSemester).toList())
                .build();
    }


    public static CurriculumDto mapToCurriculumDto(Curriculum curriculum) {
        if (curriculum == null) {
            return null;
        }
        return CurriculumDto.builder()
                .id(curriculum.getId())
                .startYear(curriculum.getStartYear())
                .endYear(curriculum.getEndYear())
                .hebrewStartYear(curriculum.getHebrewStartYear())
                .hebrewEndYear(curriculum.getHebrewEndYear())
                .semesters(curriculum.getSemesters().stream().map(SemesterMapper::mapToSemesterDto).toList())
                .build();

    }

}
