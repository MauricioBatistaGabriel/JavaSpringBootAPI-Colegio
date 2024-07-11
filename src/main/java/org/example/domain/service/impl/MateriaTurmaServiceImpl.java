package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.MateriaTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.rest.dto.CompleteMateriaTurmaDTO;
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
    public Integer save(CompleteMateriaTurmaDTO completeMateriaTurmaDTO) {
        Materia materia1 = materiaRepository
                .findById(completeMateriaTurmaDTO.getMateria())
                .orElseThrow( () ->
                        new EntityNotFoundException("Matéria com o ID:" + completeMateriaTurmaDTO.getMateria() + " naõ encontrada"));

        Turma turma1 = turmaRepository
                .findById(completeMateriaTurmaDTO.getTurma())
                .orElseThrow( () ->
                        new EntityNotFoundException("Turma com o ID:" + completeMateriaTurmaDTO.getTurma() + " não encontrada"));

        MateriaTurma materiaTurma = new MateriaTurma(materia1, turma1);
        return materiaTurmaRepository.save(materiaTurma).getId();
    }
}
