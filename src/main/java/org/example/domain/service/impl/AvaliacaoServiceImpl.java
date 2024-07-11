package org.example.domain.service.impl;

import org.example.domain.entity.Avaliacao;
import org.example.domain.entity.Materia;
import org.example.domain.entity.Turma;
import org.example.domain.repository.AvaliacaoRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.NotaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.*;
import org.example.domain.service.AvaliacaoService;
import org.example.domain.service.AvaliacaoTurmaService;
import org.example.domain.service.MateriaService;
import org.example.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private AvaliacaoTurmaService avaliacaoTurmaService;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private NotaRepository notaRepository;

    @Override
    @Transactional
    public Integer save(CompleteAvaliacaoDTO avaliacaoDTO) {
        Materia materia = materiaRepository.findById(avaliacaoDTO.getMateria())
                .orElseThrow( () ->
                        new EntityNotFoundException("Matéria com o ID:" + avaliacaoDTO.getMateria() + " não encontrada"));

        Turma turma = turmaRepository.findById(avaliacaoDTO.getTurma())
                .orElseThrow( () ->
                        new EntityNotFoundException("Turma com o ID:" + avaliacaoDTO.getTurma() + " não encontrada"));

        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNumeroAvaliacao(), materia);
        avaliacaoRepository.save(avaliacao);

        CompleteAvaliacaoTurmaDTO avaliacaoTurmaDTO = new CompleteAvaliacaoTurmaDTO(avaliacao.getId(), turma.getId());
        avaliacaoTurmaService.save(avaliacaoTurmaDTO);
        return avaliacao.getId();
    }

    @Override
    public ReturnAvaliacaoDTO findById(Integer id) {
        return avaliacaoRepository.findById(id)
                .map( avaliacao -> {
                    CompleteMateriaDTO materiaDTO = materiaService.findByID(avaliacao.getMateria().getId());
                    ReturnTurmaInOtherClassDTO turmaDTO = turmaService.findTurmaByIdAvaliacao(avaliacao.getId());
                    ReturnAvaliacaoDTO avaliacaoDTO = new ReturnAvaliacaoDTO(avaliacao.getNumeroAvaliacao(), materiaDTO, turmaDTO);
                    return avaliacaoDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Avaliacão com o ID:" + id + " não encontrada"));
    }

    @Override
    public ReturnAvaliacaoDTO findAvaliacaoByIdNota(Integer id) {
        return notaRepository.findById(id)
                .map( nota -> {
                    Avaliacao avaliacao = notaRepository.findAvalicaoByIdNota(nota.getId());
                    ReturnAvaliacaoDTO avaliacaoDTO = findById(avaliacao.getId());
                    return avaliacaoDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Nota com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<ReturnAvaliacaoDTO> filterAll(CompleteAvaliacaoDTO avaliacaoDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        CompleteMateriaDTO materiaDTO = (avaliacaoDTO.getMateria() != null) ?
                materiaService.findByID(avaliacaoDTO.getMateria()) :
                new CompleteMateriaDTO();
        Materia materia = new Materia(materiaDTO.getNome());

        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNumeroAvaliacao(), materia);

        Example example = Example.of(avaliacao, matcher);
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll(example);

        return avaliacoes.stream()
                .map(avaliacao1 -> {
                    ReturnAvaliacaoDTO avaliacaoDTO1 = findById(avaliacao1.getId());
                    return avaliacaoDTO1;
                }).collect(Collectors.toList());
    }

    @Override
    public Avaliacao update(Integer id, Avaliacao avaliacao) {
        return avaliacaoRepository.findById(id)
                .map( avaliacao1 -> {
                    avaliacao.setId(avaliacao1.getId());
                    return avaliacaoRepository.save(avaliacao);
                }).orElseThrow( () ->
                        new EntityNotFoundException("Avaliação com o ID:" + id + " não encontrada"));
    }

    @Override
    public void deleteById(Integer id) {
        avaliacaoRepository.findById(id)
                .map( avaliacao -> {
                    avaliacaoRepository.deleteById(id);
                    return Void.TYPE;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Avaliação com o ID:" + id + " não encontrada"));
    }
}
