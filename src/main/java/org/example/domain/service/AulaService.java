package org.example.domain.service;

import org.example.domain.entity.Aula;
import org.example.domain.rest.dto.AulaDTO;
import org.example.domain.rest.dto.InformacoesAulaDTO;

import java.util.List;

public interface AulaService {

    Integer save(AulaDTO aulaDTO);
    InformacoesAulaDTO findById(Integer id);
    List<Aula> filterAll(Aula aula);
    Aula update(Integer id, Aula aula);
    void deleteById(Integer id);
}
