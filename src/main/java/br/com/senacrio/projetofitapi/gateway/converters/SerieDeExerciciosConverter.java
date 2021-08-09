package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.SerieDeExerciciosDTO;
import br.com.senacrio.projetofitapi.domain.models.Nutricionista;
import br.com.senacrio.projetofitapi.domain.models.Professor;
import br.com.senacrio.projetofitapi.domain.models.SerieDeExercicios;

public class SerieDeExerciciosConverter {
    public static SerieDeExercicios toSerieDeExerciciosRequest(SerieDeExerciciosDTO dto) {

        var exercicios = dto.getExercicios().stream().map(
                exercicioDTO -> ExercicioConverter.toExercicioRequest(exercicioDTO)).toList();

        return SerieDeExercicios
                .builder()
                .nome(dto.getNome())
                .status(dto.getStatus())
                .exercicios(exercicios)
                .professor(Professor.builder().id(dto.getProfessorId()).build())
                .build();
    }

    public static SerieDeExercicios toSerieDeExerciciosUpdateRequest(SerieDeExerciciosDTO dto) {
        var exercicios = dto.getExercicios().stream().map(
                exercicioDTO -> ExercicioConverter.toExercicioUpdateRequest(exercicioDTO)).toList();

        return SerieDeExercicios
                .builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .status(dto.getStatus())
                .exercicios(exercicios)
                .professor(Professor.builder().id(dto.getProfessorId()).build())
                .build();
    }
}
