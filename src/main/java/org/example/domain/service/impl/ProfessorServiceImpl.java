package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.entity.Professor;
import org.example.domain.repository.AulaRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.ProfessorRepository;
import org.example.domain.rest.dto.*;
import org.example.domain.service.MateriaProfessorService;
import org.example.domain.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements org.example.domain.service.ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private MateriaProfessorService materiaProfessorService;

    @Override
    @Transactional
    public Integer save(CompleteProfessorDTO professorDTO) {
        Professor professor = new Professor(professorDTO.getNome(), professorDTO.getCpf());
        Professor professor1 = professorRepository.save(professor);

        if (professorDTO.getMaterias().size() != 0){

            for(Integer index = 0; index < professorDTO.getMaterias().size(); index++){
                Materia materia = materiaService.findById(professorDTO.getMaterias().get(index));

                CompleteMateriaProfessorDTO materiaProfessorDTO = new CompleteMateriaProfessorDTO(materia.getId(), professor1.getId());

                materiaProfessorService.save(materiaProfessorDTO);
            }

        }
        else{
            throw new EntityNotFoundException("Nenhuma máteria foi selecionada");
        }

        return professor.getId();
    }

    @Override
    public Professor findById(Integer id) {
        return professorRepository.findById(id)
                .map( professor -> {
                    if (professor.isPresent() == true){
                        return professor;
                    }
                    else {
                        throw new EntityNotFoundException("Professor com o ID:" + id + " foi deletado");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Professor com o ID:" + id + " não encontrado"));
    }

    @Override
    public ReturnProfessorDTO findByIdReturnDTO(Integer id) {
        Professor professor = findById(id);

        ReturnProfessorDTO professorDTO = new ReturnProfessorDTO(professor.getNome(), professor.getCpf());

        return professorDTO;
    }

    @Override
    public List<ReturnAulaInProfessorDTO> findAulaByIdProfessor(Integer id) {
        List<ReturnAulaInProfessorDTO> informacoesAulaByIdProfessorDTO = new ArrayList<>();

        return professorRepository.findById(id)
                .map( p -> {
                    aulaRepository.findAulaByIdProfessor(p.getId())
                            .map( aulas -> {

                                for (Integer i = 0; i < aulas.size(); i++) {
                                    Materia materia = materiaRepository.findById(aulas.get(i).getMateria().getId()).get();
                                    CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(materia.getNome());
                                    ReturnAulaInProfessorDTO aulaByIdProfessorDTO = new ReturnAulaInProfessorDTO(aulas.get(i).getData(), materiaDTO);
                                    informacoesAulaByIdProfessorDTO.add(aulaByIdProfessorDTO);
                                }
                                return informacoesAulaByIdProfessorDTO;
                            }).orElseThrow( () -> new EntityNotFoundException("O professor com o ID:" + id + " não possui nenhuma aula"));
                    return informacoesAulaByIdProfessorDTO;
                }).orElseThrow(() -> new EntityNotFoundException("Não existe nenhum professor com o ID:" + id));
    }

    @Override
    public List<ReturnProfessorDTO> filterAll(CompleteProfessorDTO professorDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Professor professor = new Professor(professorDTO.getNome(), professorDTO.getCpf());

        Example example = Example.of(professor, matcher);
        List<Professor> professores = professorRepository.findAll(example);

        return professores.stream()
                .map( professor1 -> new ReturnProfessorDTO(professor1.getNome(), professor1.getCpf()))
                .collect(Collectors.toList());
    }

    @Override
    public Professor update(Integer id, Professor professor) {
        Professor professor1 = findById(id);

        professor.setId(professor1.getId());

        return professorRepository.save(professor);
    }

    @Override
    public void deleteById(Integer id) {
        Professor professor = findById(id);

        professorRepository.deleteById(professor.getId());
    }
}
