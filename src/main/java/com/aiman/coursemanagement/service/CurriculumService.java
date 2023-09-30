package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.dto.CurriculumDto;
import com.aiman.coursemanagement.dto.LecturerDto;
import com.aiman.coursemanagement.entity.Curriculum;
import com.aiman.coursemanagement.entity.Lecturer;
import com.aiman.coursemanagement.mapper.CurriculumMapper;
import com.aiman.coursemanagement.mapper.LecturerMapper;
import com.aiman.coursemanagement.repository.CurriculumRepository;
import com.aiman.coursemanagement.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;

    @Autowired
    public CurriculumService(CurriculumRepository curriculumRepository) {
        this.curriculumRepository = curriculumRepository;
    }


    public void createCurriculum(CurriculumDto curriculumDto) {
        final Curriculum curriculum = CurriculumMapper.mapToCurriculum(curriculumDto);
        curriculumRepository.save(curriculum);
    }

    public List<CurriculumDto> getAllCurriculums() {
        return curriculumRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(CurriculumMapper::mapToCurriculumDto).toList();
    }

    public CurriculumDto getCurriculumById(Long id) {
        return CurriculumMapper.mapToCurriculumDto(curriculumRepository.findById(id).orElseThrow());
    }

    public void deleteCurriculum(Long curriculumId) {
        curriculumRepository.deleteById(curriculumId);
    }

    public void updateCurriculum(CurriculumDto curriculumDto) {
        final Curriculum curriculumToUpdate = curriculumRepository.findById(curriculumDto.getId()).orElseThrow();
        curriculumToUpdate.setStartYear(curriculumDto.getStartYear());
        curriculumToUpdate.setEndYear(curriculumDto.getEndYear());
        curriculumToUpdate.setHebrewStartYear(curriculumDto.getHebrewStartYear());
        curriculumToUpdate.setHebrewEndYear(curriculumDto.getHebrewEndYear());
        curriculumRepository.save(curriculumToUpdate);
    }
}
