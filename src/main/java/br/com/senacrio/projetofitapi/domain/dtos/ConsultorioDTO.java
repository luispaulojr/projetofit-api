package br.com.senacrio.projetofitapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConsultorioDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @JsonProperty(namespace = "aluno_id")
    @NotNull(message = "Valor é obrigatório!")
    private Long alunoId;

    @JsonProperty(namespace = "nutricionista_id")
    @NotNull(message = "Valor é obrigatório!")
    private Long nutricionistaId;

    @JsonProperty(namespace = "receita_id")
    @NotNull(message = "Valor é obrigatório!")
    private List<Long> receitaIds;
}
