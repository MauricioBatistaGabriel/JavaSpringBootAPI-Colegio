package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min = 1, max = 15, message = "{campo.nome-turma.validation}")
    @NotEmpty(message = "{campo.nome-turma}")
    @Column(name = "nome_turma")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    private Sala sala;

    public Turma(String nome, Sala sala) {
        this.nome = nome;
        this.sala = sala;
    }
}
