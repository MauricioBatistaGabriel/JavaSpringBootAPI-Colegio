package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.rest.dto.MateriaDTO;
import org.example.domain.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    MateriaRepository materiaRepository;

    @Override
    public Integer save(MateriaDTO materiaDTO) {
        Materia materia = new Materia(materiaDTO.getNome());
        materiaRepository.save(materia);
        return materia.getId();
    }

    @Override
    public MateriaDTO findByID(Integer id) {
        return materiaRepository.findById(id)
                .map( materia -> {
                    MateriaDTO materiaDTO = new MateriaDTO(materia.getNome());
                    return materiaDTO;
                }).orElseThrow( () -> new EntityNotFoundException("Matéria com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<Materia> filterAll(Materia materia) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example example = Example.of(materia, matcher);
        return materiaRepository.findAll(example);
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
