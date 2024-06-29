package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.MateriaTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.MateriaDTO;
import org.example.domain.service.MateriaTurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MateriaTurmaServiceImpl implements MateriaTurmaService{

    @Autowired
    private MateriaTurmaRepository materiaTurmaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Override
    public MateriaTurma save(Integer materia, Integer turma) {
        Materia materia1 = materiaRepository
                .findById(materia)
                .orElseThrow( () ->
                        new EntityNotFoundException("Matéria com o ID:" + materia + " naõ encontrada"));

        Turma turma1 = turmaRepository
                .findById(turma)
                .orElseThrow( () ->
                        new EntityNotFoundException("Turma com o ID:" + turma + " não encontrada"));

        MateriaTurma materiaTurma = new MateriaTurma(materia1, turma1);
        return materiaTurmaRepository.save(materiaTurma);
    }

    @Override
    public List<MateriaDTO> findMateriasByIdTurma(Integer id) {
        return turmaRepository.findById(id)
                .map( turma -> {
                    List<Materia> materias = materiaTurmaRepository.findMateriasByIdTurma(id);
                    List<MateriaDTO> materiasDTO = new ArrayList<>();

                    for (Integer i = 0; i < materias.size(); i++){
                        MateriaDTO materiaDTO = new MateriaDTO(materias.get(i).getNome());
                        materiasDTO.add(materiaDTO);
                    }

                    return materiasDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Turma com o ID:" + id + " não encontrada"));
    }
}
