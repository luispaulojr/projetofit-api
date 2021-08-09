package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.ExercicioDTO;
import br.com.senacrio.projetofitapi.domain.models.Exercicio;

public class ExercicioConverter {

    public static Exercicio toExercicioRequest(ExercicioDTO dto) {
        var fotos = dto.getFotos().stream().map(
                fotoDTO -> FotoConverter.toFotoRequest(fotoDTO)).toList();

        return Exercicio.builder()
                .nome(dto.getNome())
                .repeticoes(dto.getRepeticoes())
                .musculo(dto.getMusculo())
                .fotos(fotos)
                .build();
    }

    public static Exercicio toExercicioUpdateRequest(ExercicioDTO dto) {
        var fotos = dto.getFotos().stream().map(
                fotoDTO -> FotoConverter.toFotoUpdateRequest(fotoDTO)).toList();

        return Exercicio.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .repeticoes(dto.getRepeticoes())
                .musculo(dto.getMusculo())
                .fotos(fotos)
                .build();
    }
}
