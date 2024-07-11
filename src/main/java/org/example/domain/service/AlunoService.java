package org.example.domain.service;

import org.example.domain.entity.Aluno;
import org.example.domain.rest.dto.CompleteAlunoDTO;

import java.util.List;

public interface AlunoService {

    Integer save(CompleteAlunoDTO alunoDTO);

    CompleteAlunoDTO findById(Integer id);
    CompleteAlunoDTO findAlunoByIdNota(Integer id);


    List<CompleteAlunoDTO> filterAll(CompleteAlunoDTO alunoDTO);

    Aluno update(Integer id, Aluno aluno);

    void deleteById(Integer id);
}
