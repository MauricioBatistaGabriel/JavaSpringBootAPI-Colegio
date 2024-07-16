package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_materia")
    private Integer id;

    private String nome;

    public Materia(String nome) {
        this.nome = nome;
    }

    public Materia() {
    }
}
