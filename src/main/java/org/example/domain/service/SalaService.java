package org.example.domain.service;

import org.example.domain.entity.Sala;
import org.example.domain.rest.dto.SalaDTO;

import java.util.List;

public interface SalaService {

    Integer save(SalaDTO salaDTO);
    SalaDTO findById(Integer id);
    List<Sala> filterAll(Sala sala);
    Sala update(Integer id, Sala sala);
    void deleteById(Integer id);
}
