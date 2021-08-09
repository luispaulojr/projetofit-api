package br.com.senacrio.projetofitapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReceitaDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotBlank(message = "Valor é obrigatório!")
    private String objetivo;

    @NotBlank(message = "Valor é obrigatório!")
    private String nome;

    @NotNull(message = "Valor é obrigatório!")
    private List<IngredienteDTO> ingredientes;

    @NotBlank(message = "Valor é obrigatório!")
    private String preparo;

    @JsonProperty(namespace = "nutricionista_id")
    @NotNull(message = "Valor é obrigatório!")
    private Long nutricionistaId;
}
