package org.example.domain.service.impl;

import org.example.domain.entity.Aluno;
import org.example.domain.entity.AlunoTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.AlunoRepository;
import org.example.domain.repository.AlunoTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteAlunoTurmaDTO;
import org.example.domain.service.AlunoTurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class AlunoTurmaServiceImpl implements AlunoTurmaService {

    @Autowired
    private AlunoTurmaRepository alunoTurmaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Override
    public Integer save(CompleteAlunoTurmaDTO alunoTurmaDTO) {
        Aluno aluno = alunoRepository.findById(alunoTurmaDTO.getAluno())
                .orElseThrow(() ->
                        new EntityNotFoundException("Aluno com o ID:" + alunoTurmaDTO.getAluno() + " não encontrado"));

        Turma turma = turmaRepository.findById(alunoTurmaDTO.getTurma())
                .orElseThrow(() ->
                        new EntityNotFoundException("Turma com o ID:" + alunoTurmaDTO.getTurma() + " não encontrada"));

        AlunoTurma alunoTurma = new AlunoTurma(aluno, turma);
        return alunoTurmaRepository.save(alunoTurma).getId();
    }
}
