package br.com.senacrio.projetofitapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class AcademiaDTO {

    @NotBlank(message = "Valor é obrigatório!")
    private String razaoSocial;

    @NotBlank(message = "Valor é obrigatório!")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpnj;

    @NotNull(message = "Valor é obrigatório!")
    private EnderecoDTO endereco;
}
