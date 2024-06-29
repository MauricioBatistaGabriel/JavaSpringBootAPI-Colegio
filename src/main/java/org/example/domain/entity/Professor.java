package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_professor")
    private Integer id;

    @Column(name = "nome_professor")
    @Size(min = 3, max = 100, message = "{campo.nome.validation}")
    @NotEmpty(message = "{campo.nome}")
    private String nome;

    @Column(name = "cpf_professor")
    @CPF(message = "{campo.cpf.validation}")
    @NotEmpty(message = "{campo.cpf}")
    private String cpf;

    public Professor(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Professor() {
    }
}
