package org.example.domain.service;

import org.example.domain.entity.Avaliacao;
import org.example.domain.rest.dto.CompleteAvaliacaoDTO;
import org.example.domain.rest.dto.ReturnAvaliacaoDTO;

import java.util.List;

public interface AvaliacaoService {

    Integer save(CompleteAvaliacaoDTO avaliacaoDTO);
    ReturnAvaliacaoDTO findById(Integer id);
    ReturnAvaliacaoDTO findAvaliacaoByIdNota(Integer id);
    List<ReturnAvaliacaoDTO> filterAll(CompleteAvaliacaoDTO avaliacaoDTO);
    Avaliacao update(Integer id, Avaliacao avaliacao);
    void deleteById(Integer id);
}
