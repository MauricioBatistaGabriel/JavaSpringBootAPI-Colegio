package org.example.domain.service;

import org.example.domain.entity.Aula;
import org.example.domain.rest.dto.CompleteAulaDTO;
import org.example.domain.rest.dto.ReturnAulaDTO;

import java.util.List;

public interface AulaService {

    Integer save(CompleteAulaDTO aulaDTO);
    ReturnAulaDTO findById(Integer id);
    List<Aula> filterAll(Aula aula);
    Aula update(Integer id, Aula aula);
    void deleteById(Integer id);
}
