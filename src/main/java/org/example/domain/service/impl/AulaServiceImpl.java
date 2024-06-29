package org.example.domain.service.impl;

import org.example.domain.entity.Aula;
import org.example.domain.entity.Materia;
import org.example.domain.entity.Professor;
import org.example.domain.repository.AulaRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.ProfessorRepository;
import org.example.domain.rest.dto.*;
import org.example.domain.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public Integer save(AulaDTO aulaDTO) {
        Professor professor = professorRepository.findById(aulaDTO.getProfessor())
                .orElseThrow( () -> new EntityNotFoundException("Professor com o ID:" + aulaDTO.getProfessor() + " não encontrado"));

        Materia materia = materiaRepository.findById(aulaDTO.getMateria())
                .orElseThrow( () -> new EntityNotFoundException("Matéria com o ID:" + aulaDTO.getMateria() + " não encontrada"));

        Aula aula = new Aula(aulaDTO.getData(), professor, materia);
        aulaRepository.save(aula);
        return aula.getId();
    }

    @Override
    public InformacoesAulaDTO findById(Integer id) {
        return aulaRepository.findById(id)
                .map( aula -> {
                    InformacoesProfessorDTO professorDTO = new InformacoesProfessorDTO(aula.getProfessor().getNome(), aula.getProfessor().getCpf());
                    MateriaDTO materiaDTO = new MateriaDTO(aula.getMateria().getNome());
                    InformacoesAulaDTO informacoesAulaDTO = new InformacoesAulaDTO(aula.getData(), professorDTO, materiaDTO);
                    return informacoesAulaDTO;
                }).orElseThrow( () -> new EntityNotFoundException("Aula com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<Aula> filterAll(Aula aula) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example example = Example.of(aula, matcher);
        return aulaRepository.findAll(example);
    }

    @Override
    public Aula update(Integer id, Aula aula) {
        return aulaRepository.findById(id)
                .map( aula1 -> {
                    aula.setId(aula1.getId());
                    return aulaRepository.save(aula);
                }).orElseThrow( () -> new EntityNotFoundException("Aula com ID:" + id + " não encontrada"));
    }

    @Override
    public void deleteById(Integer id) {
        aulaRepository.findById(id)
                .map( aula -> {
                    aulaRepository.deleteById(id);
                    return Void.TYPE;
                }).orElseThrow( () -> new EntityNotFoundException("Aula com o ID:" + id + " não encontrada"));
    }
}
