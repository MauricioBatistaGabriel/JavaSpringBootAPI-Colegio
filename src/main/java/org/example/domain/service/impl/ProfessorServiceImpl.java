package org.example.domain.service.impl;

import org.example.domain.entity.Aula;
import org.example.domain.entity.Materia;
import org.example.domain.entity.Professor;
import org.example.domain.repository.AulaRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.ProfessorRepository;
import org.example.domain.rest.dto.*;
import org.example.domain.service.MateriaProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorServiceImpl implements org.example.domain.service.ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaProfessorService materiaProfessorService;

    @Override
    @Transactional
    public Integer save(ProfessorDTO professorDTO) {
        Professor professor = new Professor(professorDTO.getNome(), professorDTO.getCpf());
        Professor professor1 = professorRepository.save(professor);

        if (professorDTO.getMaterias().size() != 0){

            for(Integer index = 0; index < professorDTO.getMaterias().size(); index++){
                materiaRepository.findById(professorDTO.getMaterias().get(index))
                        .map( materia -> {
                            materiaProfessorService.save(materia.getId(), professor1.getId());
                            return Void.TYPE;
                        }).orElseThrow( () ->
                                new EntityNotFoundException("O professor não pode ser criado, alguma matéria não existe, ID's:" + professorDTO.getMaterias()));
            }

        }
        else{
            throw new EntityNotFoundException("Nenhuma máteria foi selecionada");
        }

        return professor.getId();
    }

    @Override
    public InformacoesProfessorDTO findById(Integer id) {
        return professorRepository.findById(id)
            .map(professor -> {
                InformacoesProfessorDTO professorDTO = new InformacoesProfessorDTO(professor.getNome(), professor.getCpf());
                return professorDTO;
            }).orElseThrow( () ->
                        new EntityNotFoundException("Professor com o ID:" + id + " não encontrado"));
    }

    @Override
    public List<AulaByIdProfessorDTO> findAulaByIdProfessor(Integer id) {
        List<AulaByIdProfessorDTO> informacoesAulaByIdProfessorDTO = new ArrayList<>();

        return professorRepository.findById(id)
                .map( p -> {
                    aulaRepository.findAulaByIdProfessor(p.getId())
                            .map( aulas -> {

                                for (Integer i = 0; i < aulas.size(); i++) {
                                    Materia materia = materiaRepository.findById(aulas.get(i).getMateria().getId()).get();
                                    MateriaDTO materiaDTO = new MateriaDTO(materia.getNome());
                                    AulaByIdProfessorDTO aulaByIdProfessorDTO = new AulaByIdProfessorDTO(aulas.get(i).getData(), materiaDTO);
                                    informacoesAulaByIdProfessorDTO.add(aulaByIdProfessorDTO);
                                }
                                return informacoesAulaByIdProfessorDTO;
                            }).orElseThrow( () -> new EntityNotFoundException("O professor com o ID:" + id + " não possui nenhuma aula"));
                    return informacoesAulaByIdProfessorDTO;
                }).orElseThrow(() -> new EntityNotFoundException("Não existe nenhum professor com o ID:" + id));
    }

    @Override
    public List<Professor> filterAll(Professor professor) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example example = Example.of(professor, matcher);
        return professorRepository.findAll(example);
    }

    @Override
    public Professor update(Integer id, Professor professor) {
        return professorRepository.findById(id)
                .map( professor1 -> {
                    professor.setId(professor1.getId());
                    return professorRepository.save(professor);
                }).orElseThrow( () ->
                        new EntityNotFoundException("Professor com o ID:" + id + " não existe"));
    }

    @Override
    public void deleteById(Integer id) {
        professorRepository.findById(id)
            .map( professor -> {
                professorRepository.deleteById(id);
                return Void.TYPE;
            }).orElseThrow( () -> new EntityNotFoundException("Professor com ID:" + id + " não encontrado"));
    }
}
