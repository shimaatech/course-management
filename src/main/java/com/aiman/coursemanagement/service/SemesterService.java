package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.CurriculumDto;
import com.aiman.coursemanagement.dto.SemesterDto;
import com.aiman.coursemanagement.entity.Curriculum;
import com.aiman.coursemanagement.entity.Semester;
import com.aiman.coursemanagement.mapper.CurriculumMapper;
import com.aiman.coursemanagement.mapper.SemesterMapper;
import com.aiman.coursemanagement.repository.CurriculumRepository;
import com.aiman.coursemanagement.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    @Autowired
    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }


    public void createCurriculum(SemesterDto semesterDto) {
        final Semester semester = SemesterMapper.mapToSemester(semesterDto);
        semesterRepository.save(semester);
    }

}
