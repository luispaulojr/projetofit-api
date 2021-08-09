package br.com.senacrio.projetofitapi.gateway.converts;

import br.com.senacrio.projetofitapi.domain.dtos.IngredienteDTO;
import br.com.senacrio.projetofitapi.domain.models.Ingrediente;

public class IngredienteConvert {
    public static Ingrediente convertIngredienteRequest(IngredienteDTO dto) {
        return Ingrediente.builder()
                .nome(dto.getNome())
                .quantidade(dto.getQuantidade())
                .tipo(dto.getTipo())
                .complemento(dto.getComplemento())
                .build();
    }

    public static Ingrediente convertIngredienteUpdateRequest(IngredienteDTO dto) {
        return Ingrediente.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .quantidade(dto.getQuantidade())
                .tipo(dto.getTipo())
                .complemento(dto.getComplemento())
                .build();
    }
}
