package br.com.senacrio.projetofitapi.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExercicioDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotBlank(message = "Valor é obrigatório!")
    private String nome;

    @NotBlank(message = "Valor é obrigatório!")
    private String musculo;

    @NotBlank(message = "Valor é obrigatório!")
    private String repeticoes;

    @JsonIgnore
    @NotNull(message = "Valor é obrigatório!")
    private SerieDeExerciciosDTO serieDeExercicios;

    @Singular
    private List<FotoDTO> fotos;
}
