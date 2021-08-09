package br.com.senacrio.projetofitapi.gateway.converts;

import br.com.senacrio.projetofitapi.domain.dtos.ReceitaDTO;
import br.com.senacrio.projetofitapi.domain.models.Nutricionista;
import br.com.senacrio.projetofitapi.domain.models.Receita;

public class ReceitaConvert {

    public static Receita convertReceitaRequest(ReceitaDTO dto) {

        var ingredientes = dto.getIngredientes().stream().map(ingredienteDTO -> IngredienteConvert.convertIngredienteRequest(ingredienteDTO)).toList();

        return Receita.builder()
                .nome(dto.getNome())
                .objetivo(dto.getObjetivo())
                .preparo(dto.getPreparo())
                .ingredientes(ingredientes)
                .nutricionista(Nutricionista.builder().id(dto.getNutricionistaId()).build())
                .build();
    }

    public static Receita convertReceitaUpdateRequest(ReceitaDTO dto) {

        var ingredientes = dto.getIngredientes().stream().map(ingredienteDTO -> IngredienteConvert.convertIngredienteRequest(ingredienteDTO)).toList();

        return Receita.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .objetivo(dto.getObjetivo())
                .preparo(dto.getPreparo())
                .ingredientes(ingredientes)
                .nutricionista(Nutricionista.builder().id(dto.getNutricionistaId()).build())
                .build();
    }
}
