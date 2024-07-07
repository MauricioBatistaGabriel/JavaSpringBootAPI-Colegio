package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnTurmaDTO {

    private String nome;

    private CompleteSalaDTO sala;

    private List<CompleteMateriaDTO> materias;
}
