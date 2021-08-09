package br.com.senacrio.projetofitapi.gateway.converts;

import br.com.senacrio.projetofitapi.domain.dtos.SerieDeExerciciosDTO;
import br.com.senacrio.projetofitapi.domain.models.SerieDeExercicios;

public class SerieDeExerciciosConvert {
    public static SerieDeExercicios convertSerieDeExerciciosRequest(SerieDeExerciciosDTO dto) {

        var exercicios = dto.getExercicios().stream().map(
                exercicioDTO -> ExercicioConvert.convertExercicioRequest(exercicioDTO)).toList();

        return SerieDeExercicios
                .builder()
                .nome(dto.getNome())
                .exercicios(exercicios)
                .build();
    }

    public static SerieDeExercicios convertSerieDeExerciciosUpdateRequest(SerieDeExerciciosDTO dto) {
        var exercicios = dto.getExercicios().stream().map(
                exercicioDTO -> ExercicioConvert.convertExercicioUpdateRequest(exercicioDTO)).toList();

        return SerieDeExercicios
                .builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .exercicios(exercicios)
                .build();
    }
}
