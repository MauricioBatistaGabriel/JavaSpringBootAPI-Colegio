package org.example.domain.service.impl;

import org.example.domain.entity.Aluno;
import org.example.domain.entity.AlunoTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.AlunoRepository;
import org.example.domain.repository.AlunoTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteAlunoTurmaDTO;
import org.example.domain.service.AlunoService;
import org.example.domain.service.AlunoTurmaService;
import org.example.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlunoTurmaServiceImpl implements AlunoTurmaService {

    @Autowired
    private AlunoTurmaRepository alunoTurmaRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

    @Override
    public Integer save(CompleteAlunoTurmaDTO alunoTurmaDTO) {
        Aluno aluno = alunoService.findById(alunoTurmaDTO.getAluno());
        Turma turma = turmaService.findById(alunoTurmaDTO.getTurma());
        AlunoTurma alunoTurma = new AlunoTurma(aluno, turma);

        return alunoTurmaRepository.save(alunoTurma).getId();
    }
}
