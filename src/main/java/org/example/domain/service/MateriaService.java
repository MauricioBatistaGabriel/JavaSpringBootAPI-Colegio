package org.example.domain.service;

import org.example.domain.entity.Materia;
import org.example.domain.rest.dto.MateriaDTO;
import java.util.List;

public interface MateriaService {

    Integer save(MateriaDTO materiaDTO);
    MateriaDTO findByID(Integer id);
    List<Materia> filterAll(Materia materia);
    Materia update(Integer id, Materia materia);
    void deleteByID(Integer id);
}
