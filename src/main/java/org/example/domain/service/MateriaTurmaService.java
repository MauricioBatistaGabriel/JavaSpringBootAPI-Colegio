package org.example.domain.service;

import org.example.domain.entity.MateriaTurma;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.rest.dto.CompleteMateriaTurmaDTO;

import java.util.List;

public interface MateriaTurmaService {

    Integer save(CompleteMateriaTurmaDTO materiaTurmaDTO);

    List<MateriaTurma> findMateriaTurmaByIdMateria(Integer id);

    List<MateriaTurma> findMateriaTurmaByIdTurma(Integer id);
}
