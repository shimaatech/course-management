package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Lecturer;
import com.aiman.coursemanagement.mapper.LecturerMapper;
import com.aiman.coursemanagement.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public List<LecturerDto> getAllLecturers() {
        return lecturerRepository.findAll().stream().map(LecturerMapper::mapToLecturerDto).toList();
    }

    public void createLecturer(LecturerDto lecturerDto) {
        final Lecturer lecturer = LecturerMapper.mapToLecturer(lecturerDto);
        lecturerRepository.save(lecturer);
    }

}
