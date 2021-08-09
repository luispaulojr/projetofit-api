package br.com.senacrio.projetofitapi.gateway.converts;

import br.com.senacrio.projetofitapi.domain.dtos.ExercicioDTO;
import br.com.senacrio.projetofitapi.domain.models.Exercicio;

public class ExercicioConvert {

    public static Exercicio convertExercicioRequest(ExercicioDTO dto) {
        var fotos = dto.getFotos().stream().map(
                fotoDTO -> FotoConvert.convertFotoRequest(fotoDTO)).toList();

        return Exercicio.builder()
                .nome(dto.getNome())
                .repeticoes(dto.getRepeticoes())
                .musculo(dto.getMusculo())
                .fotos(fotos)
                .build();
    }

    public static Exercicio convertExercicioUpdateRequest(ExercicioDTO dto) {
        var fotos = dto.getFotos().stream().map(
                fotoDTO -> FotoConvert.convertFotoUpdateRequest(fotoDTO)).toList();

        return Exercicio.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .repeticoes(dto.getRepeticoes())
                .musculo(dto.getMusculo())
                .fotos(fotos)
                .build();
    }
}
