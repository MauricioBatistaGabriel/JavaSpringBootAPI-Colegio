package org.example.domain.service.impl;

import org.example.domain.entity.Avaliacao;
import org.example.domain.entity.Materia;
import org.example.domain.repository.AvaliacaoRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.rest.dto.CompleteAvaliacaoDTO;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.rest.dto.ReturnAvaliacaoDTO;
import org.example.domain.service.AvaliacaoService;
import org.example.domain.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaService materiaService;

    @Override
    public Integer save(CompleteAvaliacaoDTO avaliacaoDTO) {
        Materia materia = materiaRepository.findById(avaliacaoDTO.getMateria())
                .orElseThrow( () ->
                        new EntityNotFoundException("Matéria com o ID:" + avaliacaoDTO.getMateria() + " não encontrada"));

        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNumeroAvaliacao(), materia);
        return avaliacaoRepository.save(avaliacao).getId();
    }

    @Override
    public ReturnAvaliacaoDTO findById(Integer id) {
        return avaliacaoRepository.findById(id)
                .map( avaliacao -> {
                    CompleteMateriaDTO materiaDTO = materiaService.findByID(avaliacao.getMateria().getId());
                    ReturnAvaliacaoDTO avaliacaoDTO = new ReturnAvaliacaoDTO(avaliacao.getNumeroAvaliacao(), materiaDTO);
                    return avaliacaoDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Avaliacão com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<Avaliacao> filterAll(Avaliacao avaliacao) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example example = Example.of(avaliacao, matcher);
        return avaliacaoRepository.findAll(example);
    }

    @Override
    public Avaliacao update(Integer id, Avaliacao avaliacao) {
        return avaliacaoRepository.findById(id)
                .map( avaliacao1 -> {
                    avaliacao.setId(avaliacao1.getId());
                    return avaliacaoRepository.save(avaliacao);
                }).orElseThrow( () ->
                        new EntityNotFoundException("Avaliação com o ID:" + id + " não encontrada"));
    }

    @Override
    public void deleteById(Integer id) {
        avaliacaoRepository.findById(id)
                .map( avaliacao -> {
                    avaliacaoRepository.deleteById(id);
                    return Void.TYPE;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Avaliação com o ID:" + id + " não encontrada"));
    }
}
