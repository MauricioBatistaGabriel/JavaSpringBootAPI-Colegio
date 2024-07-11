package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.MateriaTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private MateriaTurmaRepository materiaTurmaRepository;

    @Override
    public Integer save(CompleteMateriaDTO materiaDTO) {
        Materia materia = new Materia(materiaDTO.getNome());
        materiaRepository.save(materia);
        return materia.getId();
    }

    @Override
    public CompleteMateriaDTO findByID(Integer id) {
        return materiaRepository.findById(id)
                .map( materia -> {
                    CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(materia.getNome());
                    return materiaDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Matéria com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<CompleteMateriaDTO> findMateriasByIdTurma(Integer id) {
        return turmaRepository.findById(id)
                .map( turma -> {
                    List<Materia> materias = materiaTurmaRepository.findMateriasByIdTurma(id);
                    List<CompleteMateriaDTO> materiasDTO = new ArrayList<>();

                    for (Integer i = 0; i < materias.size(); i++){
                        CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(materias.get(i).getNome());
                        materiasDTO.add(materiaDTO);
                    }

                    return materiasDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Turma com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<CompleteMateriaDTO> filterAll(CompleteMateriaDTO materiaDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Materia materia = new Materia(materiaDTO.getNome());

        Example example = Example.of(materia, matcher);
        List<Materia> materias = materiaRepository.findAll(example);

        return materias.stream()
                .map(m -> new CompleteMateriaDTO(m.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public Materia update(Integer id, Materia materia) {
        return materiaRepository.findById(id)
                .map( materia1 -> {
                    materia.setId(materia1.getId());
                    return materiaRepository.save(materia);
                }).orElseThrow( () -> new EntityNotFoundException("Matéria com o ID:" + id + " não encontrada"));
    }

    @Override
    public void deleteByID(Integer id) {
        materiaRepository.findById(id)
                .map( materia -> {



                    materiaRepository.deleteById(id);
                    return Void.TYPE;
                }).orElseThrow( () -> new EntityNotFoundException("Matéria com o ID:" + id + " não encontrada"));
    }
}
