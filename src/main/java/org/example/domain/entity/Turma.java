package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.rest.dto.CompleteSalaDTO;

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
