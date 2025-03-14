package org.example.domain.service.impl;

import org.example.domain.entity.*;
import org.example.domain.exception.RegraNegocioException;
import org.example.domain.repository.*;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.rest.dto.ReturnMateriaDTO;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private MateriaTurmaRepository materiaTurmaRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Override
    public Integer save(CompleteMateriaDTO materiaDTO) {
        Materia materia = new Materia(materiaDTO.getNome());
        materiaRepository.save(materia);
        return materia.getId();
    }

    @Override
    public Materia findById(Integer id) {
        return materiaRepository.findById(id)
                .map( materia -> {
                    if (materia.isPresent() == true){
                        return materia;
                    }
                    else {
                    throw new EntityNotFoundException("Matéria não existe");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Matéria não encontrada"));
    }

    @Override
    public CompleteMateriaDTO findByIdReturnDTO(Integer id) {
        Materia materia = findById(id);

        CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(materia.getNome());

        return materiaDTO;
    }

    @Override
    public List<ReturnMateriaDTO> findByIdTurma(Integer id) {
        Turma turma = turmaService.findById(id);

        List<Materia> materias = materiaRepository.findByTurmaId(turma.getId());

        return materias.stream()
                .map( materia -> new ReturnMateriaDTO(materia.getId(), materia.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Materia> findByProfessorId(Integer id) {
        Professor professor = professorService.findById(id);

        List<Materia> materias = materiaRepository.findByProfessorId(professor.getId());

        return materias;
    }

    @Override
    public List<CompleteMateriaDTO> findByIdTurmaAndIdProfessor(Integer idTurma, Integer idProfessor) {
        Turma turma = turmaService.findById(idTurma);

        Professor professor = professorService.findById(idProfessor);

        List<Materia> materias = materiaRepository.findByIdTurmaAndIdProfessor(turma.getId(), professor.getId());

        List<CompleteMateriaDTO> materiasDTO = new ArrayList<>();

        materias.stream()
                .map( materia -> materiasDTO.add(new CompleteMateriaDTO(materia.getNome())))
                .collect(Collectors.toList());

        return materiasDTO;
    }

    @Override
    public List<ReturnMateriaDTO> findAll() {
        List<Materia> materias = materiaRepository.findAllOrderByIdDesc();

        return materias.stream()
                .map(m -> new ReturnMateriaDTO(m.getId(), m.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public Materia update(Integer id, Materia materia) {
        Materia materia1 = findById(id);
        materia.setId(materia1.getId());

        return materiaRepository.save(materia);
    }

    @Override
    public void deleteById(Integer id) {
        Materia materia = findById(id);

       //VALIDA SE MÁTERIA POSSUI RELAÇÃO COM PROFESSOR
        List<Professor> professores = professorService.findByMateriaId(materia.getId());

       if(!professores.isEmpty()){
           StringBuilder nomeProfessores = new StringBuilder();
           professores.stream()
                   .map( professor -> nomeProfessores.append(professor.getNome() + ", "))
                   .collect(Collectors.toList());

           throw new RegraNegocioException("Nao é possivel realizar a operação. Matéria possui relação com professor(es) " + nomeProfessores + "remova a relação.");
       }

        //VALIDA SE MATÉRIA POSSUI RELAÇÃO COM TURMA
        List<Turma> turmas = turmaService.findByMateriaId(materia.getId());

        if(!turmas.isEmpty()){
            StringBuilder nomeTurmas = new StringBuilder();

            turmas.stream()
                    .map( turma -> nomeTurmas.append(turma.getNome() + ", "))
                    .collect(Collectors.toList());
            throw new RegraNegocioException("Não é possivel realizar a operação. Materia possui relação com a(s) turma(s) " + nomeTurmas + "remova a relação");
        }

        // VALIDA SE MATÉRIA POSSUI RELAÇÃO COM AVALIAÇÃO
        List<Avaliacao> avaliacoes = avaliacaoService.findByMateriaId(materia.getId());

        if (!avaliacoes.isEmpty()){
            StringBuilder nAvaliacao = new StringBuilder();

            avaliacoes.stream()
                    .map( avaliacao -> nAvaliacao.append(avaliacao.getNumeroAvaliacao() + ", "))
                    .collect(Collectors.toList());

            throw new RegraNegocioException("Essa matéria possui relação com a(s) avaliação(ões) " + nAvaliacao + "a matéria da sua avaliação ficará com '(Excluída)' ao lado do nome.");
        }

        materia.setPresent(false);
        materiaRepository.save(materia);
    }
}
