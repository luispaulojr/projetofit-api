package br.com.senacrio.projetofitapi.domain.dtos;

import br.com.senacrio.projetofitapi.domain.enums.IngredienteType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IngredienteDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotBlank(message = "Valor é obrigatório!")
    private String nome;

    @NotNull(message = "Valor é obrigatório!")
    private Double quantidade;

    @NotNull(message = "Valor é obrigatório!")
    private IngredienteType tipo;

    @NotBlank(message = "Valor é obrigatório!")
    private String complemento;
}
