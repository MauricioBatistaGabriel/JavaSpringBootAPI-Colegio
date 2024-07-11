package org.example.domain.service.impl;

import org.example.domain.entity.*;
import org.example.domain.repository.*;
import org.example.domain.rest.dto.*;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaService salaService;

    @Override
    public Integer save(CompleteAulaDTO aulaDTO) {
        Professor professor = professorRepository.findById(aulaDTO.getProfessor())
                .orElseThrow( () -> new EntityNotFoundException("Professor com o ID:" + aulaDTO.getProfessor() + " não encontrado"));

        Materia materia = materiaRepository.findById(aulaDTO.getMateria())
                .orElseThrow( () -> new EntityNotFoundException("Matéria com o ID:" + aulaDTO.getMateria() + " não encontrada"));

        Turma turma = turmaRepository.findById(aulaDTO.getTurma())
                .orElseThrow( () -> new EntityNotFoundException("Turma com o ID:" + aulaDTO.getTurma() + " não encontrada"));

        Aula aula = new Aula(aulaDTO.getData(), professor, materia, turma);
        aulaRepository.save(aula);
        return aula.getId();
    }

    @Override
    public ReturnAulaDTO findById(Integer id) {
        return aulaRepository.findById(id)
                .map( aula -> {
                    ReturnProfessorDTO professorDTO = new ReturnProfessorDTO(aula.getProfessor().getNome(), aula.getProfessor().getCpf());
                    CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(aula.getMateria().getNome());
                    CompleteSalaDTO salaDTO = new CompleteSalaDTO(aula.getTurma().getSala().getSala());
                    ReturnTurmaInOtherClassDTO turmaDTO = new ReturnTurmaInOtherClassDTO(aula.getTurma().getNome(), salaDTO);
                    ReturnAulaDTO aulaDTO = new ReturnAulaDTO(aula.getData(), professorDTO, materiaDTO, turmaDTO);
                    return aulaDTO;
                }).orElseThrow( () -> new EntityNotFoundException("Aula com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<ReturnAulaDTO> filterAll(CompleteAulaDTO aulaDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        ReturnProfessorDTO professorDTO = (aulaDTO.getProfessor() != null) ?
            professorService.findById(aulaDTO.getProfessor()) :
                new ReturnProfessorDTO();
        Professor professor = new Professor(professorDTO.getNome(), professorDTO.getCpf());

        CompleteMateriaDTO materiaDTO = (aulaDTO.getMateria() != null) ?
            materiaService.findByID(aulaDTO.getMateria()) :
                new CompleteMateriaDTO();
        Materia materia = new Materia(materiaDTO.getNome());

        ReturnTurmaDTO turmaDTO = (aulaDTO.getTurma() != null) ?
            turmaService.findById(aulaDTO.getTurma()) :
                new ReturnTurmaDTO();

        Sala sala = new Sala(turmaDTO.getSala().getSala());

        Turma turma = new Turma(turmaDTO.getNome(), sala);

        Aula aula = new Aula(aulaDTO.getData(), professor, materia, turma);

        Example example = Example.of(aula, matcher);
        List<Aula> aulas = aulaRepository.findAll(example);

        return aulas.stream()
                .map( aula1 -> {
                    ReturnAulaDTO aulaDTO1 = findById(aula1.getId());
                    return aulaDTO1;
                }).collect(Collectors.toList());
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
