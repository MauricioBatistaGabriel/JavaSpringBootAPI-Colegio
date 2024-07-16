package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaProfessor;
import org.example.domain.entity.Professor;
import org.example.domain.repository.MateriaProfessorRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.ProfessorRepository;
import org.example.domain.rest.dto.CompleteMateriaProfessorDTO;
import org.example.domain.service.MateriaProfessorService;
import org.example.domain.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MateriaProfessorServiceImpl implements MateriaProfessorService {

    @Autowired
    private MateriaProfessorRepository materiaProfessorRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public Integer save(CompleteMateriaProfessorDTO materiaProfessorDTO) {
        Materia materia1 = materiaRepository
                .findById(materiaProfessorDTO.getMateria())
                .orElseThrow( () ->
                new EntityNotFoundException("Matéria com o ID:" + materiaProfessorDTO.getMateria() + " não encontrada"));

        Professor professor1 = professorRepository.findById(materiaProfessorDTO.getProfessor())
                .orElseThrow( () ->
                    new EntityNotFoundException("Professor com ID:" + materiaProfessorDTO.getProfessor() + " não encontrado"));

        MateriaProfessor materiaProfessor = new MateriaProfessor(materia1, professor1);
        return materiaProfessorRepository.save(materiaProfessor).getId();
    }

    @Override
    public List<MateriaProfessor> findMateriaProfessorByIdMateria(Integer id) {
        return materiaRepository.findById(id)
                .map( materia -> {
                    List<MateriaProfessor> materiaProfessorList = materiaProfessorRepository.findByMateriaId(materia.getId());
                    return materiaProfessorList;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Matéria com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<MateriaProfessor> findMateriaProfessorByIdProfessor(Integer id) {
        return professorRepository.findById(id)
                .map( professor -> {
                    List<MateriaProfessor> materiaProfessorList = materiaProfessorRepository.findByProfessorId(professor.getId());
                    return materiaProfessorList;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Professor com o ID:" + id + " não encontrado"));
    }
}
