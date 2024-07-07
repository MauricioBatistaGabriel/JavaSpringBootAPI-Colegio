package org.example.domain.service;

import org.example.domain.entity.Turma;
import org.example.domain.rest.dto.CompleteTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaDTO;

import java.util.List;

public interface TurmaService {

    Integer save(CompleteTurmaDTO turmaDTO);
    ReturnTurmaDTO findById(Integer id);
    List<Turma> filterAll(Turma turma);
    Turma update(Integer id, Turma turma);
    void deleteById(Integer id);
}
