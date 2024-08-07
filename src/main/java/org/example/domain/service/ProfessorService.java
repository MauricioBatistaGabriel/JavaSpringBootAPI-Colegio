package org.example.domain.service;

import org.example.domain.entity.Professor;
import org.example.domain.rest.dto.ReturnAulaInProfessorDTO;
import org.example.domain.rest.dto.CompleteProfessorDTO;
import org.example.domain.rest.dto.ReturnProfessorDTO;

import java.util.List;

public interface ProfessorService {

    Integer save(CompleteProfessorDTO professorDTO);
    Professor findById(Integer id);
    ReturnProfessorDTO findByIdReturnDTO(Integer id);
    List<ReturnAulaInProfessorDTO> findAulaByIdProfessor(Integer id);
    List<ReturnProfessorDTO> filterAll(CompleteProfessorDTO professorDTO);
    Professor update(Integer id, Professor professor);
    void deleteById(Integer id);
}
