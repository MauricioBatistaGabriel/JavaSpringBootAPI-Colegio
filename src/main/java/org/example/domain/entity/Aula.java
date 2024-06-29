package org.example.domain.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_aula")
    private Integer id;

    @Column(name = "data_da_aula")
    @NotEmpty(message = "{campo.data}")
    private String data;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    public Aula(String data, Professor professor, Materia materia) {
        this.data = data;
        this.professor = professor;
        this.materia = materia;
    }
}
