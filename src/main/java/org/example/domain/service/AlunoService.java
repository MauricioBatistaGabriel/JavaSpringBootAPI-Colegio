package org.example.domain.service;

import org.example.domain.entity.Aluno;
import org.example.domain.rest.dto.CompleteAlunoDTO;

import java.util.List;

public interface AlunoService {

    Integer save(CompleteAlunoDTO alunoDTO);

    CompleteAlunoDTO findByID(Integer id);

    List<Aluno> filterAll(Aluno aluno);

    Aluno update(Integer id, Aluno aluno);

    void deleteById(Integer id);
}
