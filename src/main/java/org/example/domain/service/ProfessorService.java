package org.example.domain.service;

import org.example.domain.entity.Aula;
import org.example.domain.entity.Professor;
import org.example.domain.rest.dto.AulaByIdProfessorDTO;
import org.example.domain.rest.dto.InformacoesAulaDTO;
import org.example.domain.rest.dto.ProfessorDTO;
import org.example.domain.rest.dto.InformacoesProfessorDTO;

import java.util.List;

public interface ProfessorService {

    Integer save(ProfessorDTO professorDTO);
    InformacoesProfessorDTO findById(Integer id);
    List<AulaByIdProfessorDTO> findAulaByIdProfessor(Integer id);
    List<Professor> filterAll(Professor professor);
    Professor update(Integer id, Professor professor);
    void deleteById(Integer id);
}
