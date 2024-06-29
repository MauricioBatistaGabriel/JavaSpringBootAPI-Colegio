package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformacoesAulaDTO {

    private String data;
    private InformacoesProfessorDTO professor;
    private MateriaDTO materia;
}
