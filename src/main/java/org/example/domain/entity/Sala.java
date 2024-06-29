package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sala")
    private Integer id;

    @Size(min = 1, max = 5, message = "{campo.sala.validation}")
    @NotEmpty(message = "{campo.sala}")
    @Column(name = "numero_sala")
    private String sala;

    public Sala(String sala) {
        this.sala = sala;
    }
}
