package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.ReceitaDTO;
import br.com.senacrio.projetofitapi.domain.models.Nutricionista;
import br.com.senacrio.projetofitapi.domain.models.Receita;

public class ReceitaConverter {

    public static Receita toReceitaRequest(ReceitaDTO dto) {

        var ingredientes = dto.getIngredientes().stream().map(ingredienteDTO -> IngredienteConverter.toIngredienteRequest(ingredienteDTO)).toList();

        return Receita.builder()
                .nome(dto.getNome())
                .refeicao(dto.getRefeicao())
                .horario(dto.getHorario())
                .objetivo(dto.getObjetivo())
                .preparo(dto.getPreparo())
                .ingredientes(ingredientes)
                .nutricionista(Nutricionista.builder().id(dto.getNutricionistaId()).build())
                .build();
    }

    public static Receita toReceitaUpdateRequest(ReceitaDTO dto) {

        var ingredientes = dto.getIngredientes().stream().map(ingredienteDTO -> IngredienteConverter.toIngredienteUpdateRequest(ingredienteDTO)).toList();

        return Receita.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .refeicao(dto.getRefeicao())
                .horario(dto.getHorario())
                .objetivo(dto.getObjetivo())
                .preparo(dto.getPreparo())
                .ingredientes(ingredientes)
                .nutricionista(Nutricionista.builder().id(dto.getNutricionistaId()).build())
                .build();
    }
}
