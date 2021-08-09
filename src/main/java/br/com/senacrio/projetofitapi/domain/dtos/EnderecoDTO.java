package br.com.senacrio.projetofitapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnderecoDTO {

    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotBlank(message = "Valor é obrigatório!")
    private String logradouro;

    @NotBlank(message = "Valor é obrigatório!")
    private String bairro;

    @NotBlank(message = "Valor é obrigatório!")
    private String cidade;
}
