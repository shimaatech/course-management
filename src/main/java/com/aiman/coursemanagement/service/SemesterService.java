package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.SemesterDto;
import com.aiman.coursemanagement.entity.Curriculum;
import com.aiman.coursemanagement.entity.Semester;
import com.aiman.coursemanagement.mapper.SemesterMapper;
import com.aiman.coursemanagement.repository.CurriculumRepository;
import com.aiman.coursemanagement.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    private final CurriculumRepository curriculumRepository;

    @Autowired
    public SemesterService(SemesterRepository semesterRepository, CurriculumRepository curriculumRepository) {
        this.semesterRepository = semesterRepository;
        this.curriculumRepository = curriculumRepository;
    }


    public void createSemester(SemesterDto semesterDto, Long curriculumId) {
        final Semester semester = SemesterMapper.mapToSemester(semesterDto);
        semesterRepository.save(semester);
        final Curriculum curriculum = curriculumRepository.findById(curriculumId).orElseThrow();
        List<Semester> curriculumSemesters = new ArrayList<>(curriculum.getSemesters());
        curriculumSemesters.add(semester);
        curriculum.setSemesters(curriculumSemesters);
        curriculumRepository.save(curriculum);
    }
}
