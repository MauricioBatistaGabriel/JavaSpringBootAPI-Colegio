package org.example.domain.service.impl;

import org.example.domain.entity.*;
import org.example.domain.exception.EntityNotDisponibleException;
import org.example.domain.repository.*;
import org.example.domain.rest.dto.*;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaServiceImpl implements TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private SalaService salaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorTurmaService professorTurmaService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private MateriaTurmaService materiaTurmaService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoTurmaService alunoTurmaService;

    @Autowired
    private AvaliacaoTurmaRepository avaliacaoTurmaRepository;

    @Override
    @Transactional
    public Integer save(CompleteTurmaDTO turmaDTO) {
        Sala sala = salaService.findById(turmaDTO.getSala());

        Turma turma = new Turma(turmaDTO.getNome(), sala, turmaDTO.getPeriodo());
        Turma turma1 = turmaRepository.save(turma);

        //Valida se periodo da sala está disponivel para a turma
        if (!sala.getPeriodosDisponiveis().contains(turmaDTO.getPeriodo())){
            throw new EntityNotDisponibleException("A sala já possui turma nesse periodo");
        }

        //Remove o periodo da sala utilizado pela turma criada como fora da lista de periodos disponiveis
        sala.getPeriodosDisponiveis().remove(turmaDTO.getPeriodo());
        salaService.update(sala.getId(), sala);

//      CRIA RELAÇÃO ALUNO-TURMA
        if (turmaDTO.getAlunos().size() != 0){

            for(Integer index = 0; index < turmaDTO.getAlunos().size(); index++){
                Aluno aluno = alunoService.findById(turmaDTO.getAlunos().get(index));

                CompleteAlunoTurmaDTO alunoTurmaDTO = new CompleteAlunoTurmaDTO(aluno.getId(), turma1.getId());

                alunoTurmaService.save(alunoTurmaDTO);
            }

        }
        else{
            throw new EntityNotFoundException("Nenhum aluno foi selecionado");
        }

//      CRIA RELAÇÃO MATÉRIA-TURMA
        if (turmaDTO.getMaterias().size() != 0){
            turmaDTO.getMaterias().stream()
                    .map(materiaId -> materiaService.findById(materiaId))
                    .map(materia -> new CompleteMateriaTurmaDTO(materia.getId(), turma1.getId()))
                    .forEach(materiaTurmaDTO -> materiaTurmaService.save(materiaTurmaDTO));
        }
        else{
            throw new EntityNotDisponibleException("Nenhuma máteria foi selecionada");
        }

        //CRIA RELAÇÃO PROFESSOR-TURMA
        if (turmaDTO.getProfessores().size() != 0){
            turmaDTO.getProfessores().stream()
                    .map(professorId -> professorService.findById(professorId))
                    .map(professor -> new CompleteProfessorTurmaDTO(professor.getId(), turma1.getId()))
                    .forEach(professorTurmaDTO -> professorTurmaService.save(professorTurmaDTO));
        }
        else{
            throw new EntityNotDisponibleException("Nenhum professor foi selecionado");
        }

        return turma1.getId();
    }

    @Override
    public Turma findById(Integer id) {
        return turmaRepository.findById(id)
                .map( turma -> {
                    if (turma.isPresent() == true) {
                        return turma;
                    }
                    else {
                        throw new EntityNotFoundException("Turma não existe");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Turma não encontrada"));
    }

    @Override
    public ReturnTurmaDTO findByIdReturnDTO(Integer id) {
        Turma turma = findById(id);

        CompleteSalaDTO salaDTO = salaService.findByIdReturnDTO(turma.getSala().getId());
        List<CompleteMateriaDTO> materiaDTOS = materiaService.findByIdTurma(turma.getId());
        List<ReturnProfessorDTO> professoresDTO = professorService.findProfessoresDTOByIdTurma(turma.getId());
        ReturnTurmaDTO turmaDTO = new ReturnTurmaDTO(turma.getNome(), turma.getPeriodo(), salaDTO, materiaDTOS, professoresDTO);

        return turmaDTO;
    }

    @Override
    public ReturnTurmaInOtherClassDTO findByIdTurmaInOtherClass(Integer id) {
        Turma turma = findById(id);

        CompleteSalaDTO salaDTO = new CompleteSalaDTO(turma.getSala().getSala(), turma.getSala().getPeriodosDisponiveis());
        ReturnTurmaInOtherClassDTO turmaDTO = new ReturnTurmaInOtherClassDTO(turma.getNome(), salaDTO);

        return turmaDTO;
    }

    @Override
    public ReturnTurmaInOtherClassDTO findTurmaByIdAvaliacao(Integer id) {
        Turma turma = avaliacaoTurmaRepository.findTurmaByIdAvaliacao(id);
        CompleteSalaDTO salaDTO = new CompleteSalaDTO(turma.getSala().getSala(), turma.getSala().getPeriodosDisponiveis());
        ReturnTurmaInOtherClassDTO turmaDTO = new ReturnTurmaInOtherClassDTO(turma.getNome(), salaDTO);
        return turmaDTO;
    }

    @Override
    public List<Turma> findByMateriaId(Integer id) {
        Materia materia = materiaService.findById(id);

        List<Turma> turmas = turmaRepository.findByMateriaId(materia.getId());
        return turmas;
    }

    //MÉTODO NÃO BUSCA TURMA POR MATÉRIA
    @Override
    public List<ReturnTurmaDTO> filterAll(CompleteTurmaDTO turmaDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        CompleteSalaDTO salaDTO = (turmaDTO.getSala() != null) ?
                salaService.findByIdReturnDTO(turmaDTO.getSala()) :
                new CompleteSalaDTO();
        Sala sala = new Sala(salaDTO.getSala());

        Turma turma = new Turma(turmaDTO.getNome(), sala);

        Example example = Example.of(turma, matcher);
        List<Turma> turmas = turmaRepository.findAll(example);

        return turmas.stream()
                .map( turma1 -> {
                    ReturnTurmaDTO turmaDTO1 = findByIdReturnDTO(turma1.getId());
                    return turmaDTO1;
                }).collect(Collectors.toList());
    }

    @Override
    public Turma update(Integer id, Turma turma) {
        Turma turma1 = findById(id);

        turma.setId(turma1.getId());

        return turmaRepository.save(turma);
    }

    @Override
    public void deleteById(Integer id) {
        Turma turma = findById(id);

        turma.setPresent(false);

        turmaRepository.save(turma);
    }
}
