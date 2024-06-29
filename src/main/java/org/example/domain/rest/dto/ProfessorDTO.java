package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO {

    @Size(min = 3, max = 100, message = "{campo.nome.validation}")
    @NotEmpty(message = "{campo.nome}")
    private String nome;

    @CPF(message = "{campo.cpf.validation}")
    @NotEmpty(message = "{campo.cpf}")
    private String cpf;

    @NotNull(message = "{campo.materia}")
    private List<Integer> materias;

    public ProfessorDTO(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
}
