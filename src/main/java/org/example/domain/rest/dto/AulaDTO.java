package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AulaDTO {

    @NotEmpty(message = "{campo.data}")
    private String data;

    @NotNull(message = "{campo.professor}")
    private Integer professor;

    @NotNull(message = "{campo.materia}")
    private Integer materia;
}
