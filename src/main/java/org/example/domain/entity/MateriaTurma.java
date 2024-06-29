package org.example.domain.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Setter
@Getter
public class MateriaTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_materia_turma")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    public MateriaTurma(Materia materia, Turma turma) {
        this.materia = materia;
        this.turma = turma;
    }
}
