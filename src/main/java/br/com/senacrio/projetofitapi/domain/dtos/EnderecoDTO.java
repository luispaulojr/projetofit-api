package br.com.senacrio.projetofitapi.domain.dtos;

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

    @NotBlank(message = "Valor é obrigatório!")
    private String logradouro;

    @NotBlank(message = "Valor é obrigatório!")
    private String bairro;

    @NotBlank(message = "Valor é obrigatório!")
    private String cidade;
}
