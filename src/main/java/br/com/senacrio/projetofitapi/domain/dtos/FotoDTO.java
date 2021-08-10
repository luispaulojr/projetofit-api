package br.com.senacrio.projetofitapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
public class FotoDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private String strFoto;

    @JsonProperty(namespace = "exercicio_id", access = JsonProperty.Access.WRITE_ONLY)
    private Long exercicioId;
}
