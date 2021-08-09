package br.com.senacrio.projetofitapi.domain.dtos;

import br.com.senacrio.projetofitapi.domain.enums.SerieDeExerciciosStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SerieDeExerciciosDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotBlank(message = "Valor é obrigatório!")
    private String nome;
    
    @Enumerated(EnumType.STRING)
    private SerieDeExerciciosStatus status = SerieDeExerciciosStatus.INATIVA;

    @NotNull(message = "Valor é obrigatório!")
    private List<ExercicioDTO> exercicios;

    @JsonProperty(namespace = "nutricionista_id")
    @NotNull(message = "Valor é obrigatório!")
    private Long professorId;
}
