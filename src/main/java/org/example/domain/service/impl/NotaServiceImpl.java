package org.example.domain.service.impl;

import org.example.domain.entity.Aluno;
import org.example.domain.entity.Avaliacao;
import org.example.domain.entity.Materia;
import org.example.domain.entity.Nota;
import org.example.domain.repository.AlunoRepository;
import org.example.domain.repository.AvaliacaoRepository;
import org.example.domain.repository.NotaRepository;
import org.example.domain.rest.dto.*;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private AvaliacaoTurmaService avaliacaoTurmaService;

    @Override
    public Integer save(CompleteNotaDTO notaDTO) {
        Aluno aluno = alunoRepository.findById(notaDTO.getAluno())
                .orElseThrow( () ->
                        new EntityNotFoundException("Aluno com o ID:" + notaDTO.getAluno() + " não encontrado"));

        Avaliacao avaliacao = avaliacaoRepository.findById(notaDTO.getAvaliacao())
                .orElseThrow( () ->
                        new EntityNotFoundException("Avaliação com o ID:" + notaDTO.getAvaliacao() + " não encontrada"));

        Nota nota = new Nota(notaDTO.getNota(), aluno, avaliacao);
        notaRepository.save(nota);
        return nota.getId();
    }

    @Override
    public ReturnNotaDTO findById(Integer id) {
        return notaRepository.findById(id)
                .map( nota -> {
                    ReturnAvaliacaoDTO avaliacaoDTO = avaliacaoService.findAvaliacaoByIdNota(nota.getId());

                    CompleteAlunoDTO alunoDTO = alunoService.findAlunoByIdNota(nota.getId());

                    ReturnNotaDTO notaDTO = new ReturnNotaDTO(nota.getNota(), avaliacaoDTO, alunoDTO);
                    return notaDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Nota com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<ReturnNotaDTO> filterAll(CompleteNotaDTO notaDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        CompleteAlunoDTO alunoDTO = (notaDTO.getAluno() != null) ?
                alunoService.findById(notaDTO.getAluno()) :
                new CompleteAlunoDTO();
        Aluno aluno = new Aluno(alunoDTO.getNome(), alunoDTO.getCpf(), alunoDTO.getIdade());

        CompleteMateriaDTO materiaDTO = (notaDTO.getAvaliacao() != null) ?
                avaliacaoService.findById(notaDTO.getAvaliacao()).getMateria() :
                new CompleteMateriaDTO();
        Materia materia = new Materia(materiaDTO.getNome());


        ReturnAvaliacaoDTO avaliacaoDTO = (notaDTO.getAvaliacao() != null) ?
                avaliacaoService.findById(notaDTO.getAvaliacao()) :
                new ReturnAvaliacaoDTO();

        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNumeroAvaliacao(), materia);

        Nota nota = new Nota(notaDTO.getNota(), aluno, avaliacao);

        Example example = Example.of(nota, matcher);
        List<Nota> notas = notaRepository.findAll(example);

        return notas.stream()
                .map( nota1 -> {
                    ReturnNotaDTO notaDTO1 = findById(nota1.getId());
                    return notaDTO1;
                }).collect(Collectors.toList());
    }

    @Override
    public Nota update(Integer id, Nota nota) {
        return notaRepository.findById(id)
                .map( nota1 -> {
                    nota.setId(nota1.getId());
                    return notaRepository.save(nota);
                }).orElseThrow( () ->
                        new EntityNotFoundException("Nota com o ID:" + id + " não encontrada"));
    }

    @Override
    public void deleteById(Integer id) {
        notaRepository.findById(id)
                .map( nota -> {
                    notaRepository.deleteById(id);
                    return Void.TYPE;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Nota com o ID:" + id + " não encontrada"));
    }
}
