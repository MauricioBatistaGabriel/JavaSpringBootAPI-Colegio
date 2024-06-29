package org.example.domain.service;

import org.example.domain.entity.MateriaTurma;
import org.example.domain.rest.dto.MateriaDTO;

import java.util.List;

public interface MateriaTurmaService {

    MateriaTurma save(Integer materia, Integer turma);

    List<MateriaDTO> findMateriasByIdTurma(Integer id);
}
