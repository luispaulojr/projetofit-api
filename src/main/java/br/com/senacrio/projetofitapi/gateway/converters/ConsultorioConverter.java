package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.ConsultorioDTO;
import br.com.senacrio.projetofitapi.domain.models.Aluno;
import br.com.senacrio.projetofitapi.domain.models.Consultorio;
import br.com.senacrio.projetofitapi.domain.models.Nutricionista;
import br.com.senacrio.projetofitapi.domain.models.Receita;

public class ConsultorioConverter {

    public static Consultorio toConsultorioRequest(ConsultorioDTO dto) {

        var receitas = dto.getReceitaIds().stream().map(
                ReceitaId -> Receita.builder().id(ReceitaId
            ).build()).toList();

        return Consultorio
                .builder()
                .aluno(Aluno.builder().id(dto.getAlunoId()).build())
                .nutricionista(Nutricionista.builder().id(dto.getNutricionistaId()).build())
                .receitas(receitas)
                .build();
    }

    public static Consultorio toConsultorioUpdateRequest(ConsultorioDTO dto) {

        var receitas = dto.getReceitaIds().stream().map(
                ReceitaId -> Receita.builder().id(ReceitaId).build()
            ).toList();

        return Consultorio
                .builder()
                .id(dto.getId())
                .aluno(Aluno.builder().id(dto.getAlunoId()).build())
                .nutricionista(Nutricionista.builder().id(dto.getNutricionistaId()).build())
                .receitas(receitas)
                .build();
    }
}
