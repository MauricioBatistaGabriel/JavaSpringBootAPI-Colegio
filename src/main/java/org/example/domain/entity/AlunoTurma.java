package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AlunoTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_aluno_turma")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    public AlunoTurma(Aluno aluno, Turma turma) {
        this.aluno = aluno;
        this.turma = turma;
    }
}
