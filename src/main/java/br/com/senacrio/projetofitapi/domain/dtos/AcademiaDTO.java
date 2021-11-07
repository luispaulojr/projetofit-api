package br.com.senacrio.projetofitapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Data
public class AcademiaDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotBlank(message = "Valor é obrigatório!")
    private String razaoSocial;

    @NotBlank(message = "Valor é obrigatório!")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cnpj;

    @NotNull(message = "Valor é obrigatório!")
    private EnderecoDTO endereco;
}
