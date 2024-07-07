package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteSalaDTO {

    @Size(min = 1, max = 5, message = "{campo.sala.validation}")
    @NotEmpty(message = "{campo.sala}")
    private String sala;
}
