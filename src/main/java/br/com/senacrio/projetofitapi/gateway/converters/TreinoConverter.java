package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.TreinoDTO;
import br.com.senacrio.projetofitapi.domain.models.Aluno;
import br.com.senacrio.projetofitapi.domain.models.Professor;
import br.com.senacrio.projetofitapi.domain.models.SerieDeExercicios;
import br.com.senacrio.projetofitapi.domain.models.Treino;

public class TreinoConverter {

    public static Treino toTreinoRequest(TreinoDTO dto) {
        var serieList = dto.getSerieDeExerciciosId().stream().map(
                serieId -> SerieDeExercicios.builder().id(serieId).build()
            ).toList();

        return Treino.builder()
                .aluno(Aluno.builder().id(dto.getAlunoId()).build())
                .professor(Professor.builder().id(dto.getProfessorId()).build())
                .serieDeExerciciosList(serieList)
                .build();
    }
    public static Treino toTreinoUpdateRequest(TreinoDTO dto) {
        var serieList = dto.getSerieDeExerciciosId().stream().map(
                serieId -> SerieDeExercicios.builder().id(serieId).build()
        ).toList();

        return Treino.builder()
                .id(dto.getId())
                .aluno(Aluno.builder().id(dto.getAlunoId()).build())
                .professor(Professor.builder().id(dto.getProfessorId()).build())
                .serieDeExerciciosList(serieList)
                .build();
    }
}
