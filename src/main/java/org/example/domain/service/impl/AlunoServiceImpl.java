package org.example.domain.service.impl;

import org.example.domain.entity.Aluno;
import org.example.domain.repository.AlunoRepository;
import org.example.domain.repository.NotaRepository;
import org.example.domain.rest.dto.CompleteAlunoDTO;
import org.example.domain.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoServiceImpl implements AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Override
    public Integer save(CompleteAlunoDTO alunoDTO) {
        Aluno aluno = new Aluno(alunoDTO.getNome(), alunoDTO.getCpf(), alunoDTO.getIdade());
        alunoRepository.save(aluno);
        return aluno.getId();
    }

    @Override
    public CompleteAlunoDTO findById(Integer id) {
        return alunoRepository.findById(id)
                .map( aluno -> {
                    CompleteAlunoDTO alunoDTO = new CompleteAlunoDTO(aluno.getNome(), aluno.getCpf(), aluno.getIdade());
                    return alunoDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Aluno com o ID:" + id + " não encontrado"));
    }

    @Override
    public CompleteAlunoDTO findAlunoByIdNota(Integer id) {
        return notaRepository.findById(id)
                .map( nota -> {
                    Aluno aluno = notaRepository.findAlunoByIdNota(id);
                    CompleteAlunoDTO alunoDTO = findById(aluno.getId());
                    return alunoDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Nota com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<CompleteAlunoDTO> filterAll(CompleteAlunoDTO alunoDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Aluno aluno = new Aluno(alunoDTO.getNome(), alunoDTO.getCpf(), alunoDTO.getIdade());

        Example example = Example.of(aluno, matcher);
        List<Aluno> alunos = alunoRepository.findAll(example);

        return alunos.stream()
                .map( aluno1 -> new CompleteAlunoDTO(aluno1.getNome(), aluno1.getCpf(), aluno1.getIdade()))
                .collect(Collectors.toList());
    }

    @Override
    public Aluno update(Integer id, Aluno aluno) {
        return alunoRepository.findById(id)
                .map( aluno1 -> {
                    aluno.setId(aluno1.getId());
                    return alunoRepository.save(aluno);
                }).orElseThrow( () ->
                        new EntityNotFoundException("Aluno com o ID:" + id + " não encontrado"));
    }

    @Override
    public void deleteById(Integer id) {
        alunoRepository.findById(id)
                .map( aluno -> {
                    alunoRepository.deleteById(aluno.getId());
                    return Void.TYPE;
                }).orElseThrow( () ->
                    new EntityNotFoundException("Aluno com o ID:" + id + " não encontrado"));
    }
}
