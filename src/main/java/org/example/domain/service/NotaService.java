package org.example.domain.service;

import org.example.domain.entity.Nota;
import org.example.domain.rest.dto.CompleteAlunoDTO;
import org.example.domain.rest.dto.CompleteNotaDTO;
import org.example.domain.rest.dto.ReturnAvaliacaoDTO;
import org.example.domain.rest.dto.ReturnNotaDTO;

import java.util.List;

public interface NotaService {

    Integer save(CompleteNotaDTO notaDTO);
    ReturnNotaDTO findById(Integer id);
    List<ReturnNotaDTO> filterAll(CompleteNotaDTO notaDTO);
    Nota update(Integer id, Nota nota);
    void deleteById(Integer id);
}
