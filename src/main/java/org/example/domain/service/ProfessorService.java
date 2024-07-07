package org.example.domain.service;

import org.example.domain.entity.Professor;
import org.example.domain.rest.dto.ReturnAulaInProfessorDTO;
import org.example.domain.rest.dto.CompleteProfessorDTO;
import org.example.domain.rest.dto.ReturnProfessorDTO;

import java.util.List;

public interface ProfessorService {

    Integer save(CompleteProfessorDTO professorDTO);
    ReturnProfessorDTO findById(Integer id);
    List<ReturnAulaInProfessorDTO> findAulaByIdProfessor(Integer id);
    List<Professor> filterAll(Professor professor);
    Professor update(Integer id, Professor professor);
    void deleteById(Integer id);
}
