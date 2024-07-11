package org.example.domain.service;

import org.example.domain.entity.Materia;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import java.util.List;

public interface MateriaService {

    Integer save(CompleteMateriaDTO materiaDTO);
    CompleteMateriaDTO findByID(Integer id);
    List<CompleteMateriaDTO> findMateriasByIdTurma(Integer id);
    List<CompleteMateriaDTO> filterAll(CompleteMateriaDTO materiaDTO);
    Materia update(Integer id, Materia materia);
    void deleteByID(Integer id);
}
