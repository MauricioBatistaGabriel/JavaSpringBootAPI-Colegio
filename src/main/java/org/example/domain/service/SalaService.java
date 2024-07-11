package org.example.domain.service;

import org.example.domain.entity.Sala;
import org.example.domain.rest.dto.CompleteSalaDTO;

import java.util.List;

public interface SalaService {

    Integer save(CompleteSalaDTO salaDTO);
    CompleteSalaDTO findById(Integer id);
    List<CompleteSalaDTO> filterAll(CompleteSalaDTO salaDTO);
    Sala update(Integer id, Sala sala);
    void deleteById(Integer id);
}
