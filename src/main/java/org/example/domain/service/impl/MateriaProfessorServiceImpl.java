package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaProfessor;
import org.example.domain.entity.Professor;
import org.example.domain.repository.MateriaProfessorRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.ProfessorRepository;
import org.example.domain.service.MateriaProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class MateriaProfessorServiceImpl implements MateriaProfessorService {

    @Autowired
    private MateriaProfessorRepository materiaProfessorRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public MateriaProfessor save(Integer materia, Integer professor) {
        Materia materia1 = materiaRepository
                .findById(materia)
                .orElseThrow( () ->
                new EntityNotFoundException("Matéria com o ID:" + materia + " não encontrada"));

        Professor professor1 = professorRepository.findById(professor)
                .orElseThrow( () ->
                    new EntityNotFoundException("Professor com ID:" + professor + " não encontrado"));

        MateriaProfessor materiaProfessor = new MateriaProfessor(materia1, professor1);
        return materiaProfessorRepository.save(materiaProfessor);
    }
}
