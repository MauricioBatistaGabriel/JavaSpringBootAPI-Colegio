package org.example.domain.service;

import org.example.domain.rest.dto.CompleteAvaliacaoTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaInOtherClassDTO;

public interface AvaliacaoTurmaService {

    Integer save(CompleteAvaliacaoTurmaDTO avaliacaoTurmaDTO);
}
