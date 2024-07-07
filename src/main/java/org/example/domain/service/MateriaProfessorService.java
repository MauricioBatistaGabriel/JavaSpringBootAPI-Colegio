package org.example.domain.service;

import org.example.domain.entity.MateriaProfessor;
import org.example.domain.rest.dto.CompleteMateriaProfessorDTO;

public interface MateriaProfessorService {

    Integer save(CompleteMateriaProfessorDTO materiaProfessorDTO);
}
