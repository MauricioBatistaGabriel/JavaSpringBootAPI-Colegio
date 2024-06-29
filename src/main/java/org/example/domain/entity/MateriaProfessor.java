package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class MateriaProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_materia_professor")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    public MateriaProfessor(Materia materia, Professor professor) {
        this.materia = materia;
        this.professor = professor;
    }
}
