package org.example.domain.service;

import org.example.domain.entity.Turma;
import org.example.domain.rest.dto.TurmaDTO;
import org.example.domain.rest.dto.InformacoesTurmaDTO;

import java.util.List;

public interface TurmaService {

    Integer save(TurmaDTO turmaDTO);
    InformacoesTurmaDTO findById(Integer id);
    List<Turma> filterAll(Turma turma);
    Turma update(Integer id, Turma turma);
    void deleteById(Integer id);
}
