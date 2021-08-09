package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.IngredienteDTO;
import br.com.senacrio.projetofitapi.domain.models.Ingrediente;

public class IngredienteConverter {
    public static Ingrediente toIngredienteRequest(IngredienteDTO dto) {
        return Ingrediente.builder()
                .nome(dto.getNome())
                .quantidade(dto.getQuantidade())
                .tipo(dto.getTipo())
                .complemento(dto.getComplemento())
                .build();
    }

    public static Ingrediente toIngredienteUpdateRequest(IngredienteDTO dto) {
        return Ingrediente.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .quantidade(dto.getQuantidade())
                .tipo(dto.getTipo())
                .complemento(dto.getComplemento())
                .build();
    }
}
