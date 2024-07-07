package org.example.domain.service.impl;

import org.example.domain.entity.Sala;
import org.example.domain.entity.Turma;
import org.example.domain.repository.AlunoRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.SalaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteAlunoTurmaDTO;
import org.example.domain.rest.dto.CompleteMateriaTurmaDTO;
import org.example.domain.rest.dto.CompleteTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaDTO;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TurmaServiceImpl implements TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaService salaService;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private MateriaTurmaService materiaTurmaService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoTurmaService alunoTurmaService;

    @Override
    @Transactional
    public Integer save(CompleteTurmaDTO turmaDTO) {

        Sala sala = salaRepository.findById(turmaDTO.getSala())
                .orElseThrow( () ->
                        new EntityNotFoundException("Sala com ID:" + turmaDTO.getSala() + " não encontrada"));


        Turma turma = new Turma(turmaDTO.getNome(), sala);
        Turma turma1 = turmaRepository.save(turma);

//      CRIA RELAÇÃO ALUNO-TURMA
        if (turmaDTO.getAlunos().size() != 0){

            for(Integer index = 0; index < turmaDTO.getAlunos().size(); index++){
                alunoRepository.findById(turmaDTO.getAlunos().get(index))
                        .map( aluno -> {
                            CompleteAlunoTurmaDTO alunoTurmaDTO = new CompleteAlunoTurmaDTO(aluno.getId(), turma1.getId());
                            alunoTurmaService.save(alunoTurmaDTO);
                            return Void.TYPE;
                        }).orElseThrow( () ->
                                new EntityNotFoundException("A turma não pode ser criada, algum aluno não existe: " + turmaDTO.getMaterias()));
            }

        }
        else{
            throw new EntityNotFoundException("Nenhum aluno foi selecionado");
        }

//      CRIA RELAÇÃO MATÉRIA-TURMA
        if (turmaDTO.getMaterias().size() != 0){

            for(Integer index = 0; index < turmaDTO.getMaterias().size(); index++){
                materiaRepository.findById(turmaDTO.getMaterias().get(index))
                        .map( materia -> {
                            CompleteMateriaTurmaDTO materiaTurmaDTO = new CompleteMateriaTurmaDTO(materia.getId(), turma1.getId());
                            materiaTurmaService.save(materiaTurmaDTO);
                            return Void.TYPE;
                        }).orElseThrow( () ->
                                new EntityNotFoundException("A turma não pode ser criada, a matéria com ID:" + turmaDTO.getMaterias() + " não existe"));
            }

        }
        else{
            throw new EntityNotFoundException("Nenhuma máteria foi selecionada");
        }

        return turma1.getId();
    }

    @Override
    public ReturnTurmaDTO findById(Integer id) {
        return turmaRepository.findById(id)
                .map( turma -> {
                    ReturnTurmaDTO informacoesTurmaDTO = new ReturnTurmaDTO(turma.getNome(), salaService.findById(turma.getSala().getId()), materiaTurmaService.findMateriasByIdTurma(id));
                    return informacoesTurmaDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Turma com ID:" + id + " não encontrada"));
    }

    @Override
    public List<Turma> filterAll(Turma turma) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example example = Example.of(turma, matcher);
        return turmaRepository.findAll(example);
    }

    @Override
    public Turma update(Integer id, Turma turma) {
        return turmaRepository.findById(id)
                .map( turma1 -> {
                    turma.setId(turma1.getId());
                    return turmaRepository.save(turma);
                }).orElseThrow( () ->
                        new EntityNotFoundException("Turma com o ID:" + id + " não encontrada"));
    }

    @Override
    public void deleteById(Integer id) {
        turmaRepository.findById(id)
                .map( turma -> {
                    turmaRepository.deleteById(id);
                    return Void.TYPE;
                }).orElseThrow( ()
                        -> new EntityNotFoundException("Turma com o ID:" + id + " não encontrada"));
    }
}
