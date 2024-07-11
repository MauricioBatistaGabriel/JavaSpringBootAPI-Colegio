package org.example.domain.service.impl;

import org.example.domain.entity.Avaliacao;
import org.example.domain.entity.AvaliacaoTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.AvaliacaoRepository;
import org.example.domain.repository.AvaliacaoTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteAvaliacaoTurmaDTO;
import org.example.domain.rest.dto.CompleteSalaDTO;
import org.example.domain.rest.dto.ReturnTurmaInOtherClassDTO;
import org.example.domain.service.AvaliacaoTurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class AvaliacaoTurmaServiceImpl implements AvaliacaoTurmaService {

    @Autowired
    private AvaliacaoTurmaRepository avaliacaoTurmaRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Override
    public Integer save(CompleteAvaliacaoTurmaDTO avaliacaoTurmaDTO) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoTurmaDTO.getAvaliacao())
                .orElseThrow( () ->
                        new EntityNotFoundException("Avaliação com o ID:" + avaliacaoTurmaDTO.getAvaliacao() + " não encontrada"));

        Turma turma = turmaRepository.findById(avaliacaoTurmaDTO.getTurma())
                .orElseThrow( () ->
                        new EntityNotFoundException("Turma com o ID:" + avaliacaoTurmaDTO.getTurma() + " não encontrada"));

        AvaliacaoTurma avaliacaoTurma = new AvaliacaoTurma(avaliacao, turma);
        return avaliacaoTurmaRepository.save(avaliacaoTurma).getId();
    }
}
