package com.aiman.coursemanagement.dto;


import com.aiman.coursemanagement.entity.Course;
import com.aiman.coursemanagement.entity.Semester;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumDto {


    private Long id;

    private Integer startYear;

    private Integer endYear;

    private String hebrewStartYear;

    private String hebrewEndYear;

    private List<SemesterDto> semesters = new ArrayList<>();

}
