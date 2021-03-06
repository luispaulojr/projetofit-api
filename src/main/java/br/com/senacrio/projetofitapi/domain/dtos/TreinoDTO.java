package br.com.senacrio.projetofitapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Data
public class TreinoDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @JsonProperty(namespace = "aluno_id")
    @NotNull(message = "Valor é obrigatório!")
    private Long alunoId;

    @JsonProperty(namespace = "professor_id")
    @NotNull(message = "Valor é obrigatório!")
    private Long professorId;

    @JsonProperty(namespace = "receita_id")
    @NotNull(message = "Valor é obrigatório!")
    private List<Long> serieDeExerciciosId;
}
