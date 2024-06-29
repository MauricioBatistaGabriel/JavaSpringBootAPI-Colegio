package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformacoesTurmaDTO {

    private String nomeDaTurma;

    private SalaDTO sala;

    private List<MateriaDTO> materias;
}
