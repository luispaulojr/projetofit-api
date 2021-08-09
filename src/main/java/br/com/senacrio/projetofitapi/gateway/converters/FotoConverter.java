package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.FotoDTO;
import br.com.senacrio.projetofitapi.domain.models.Foto;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class FotoConverter {
    public static Foto toFotoRequest(FotoDTO dto) {
        return Foto.builder()
                .strFoto(dto.getStrFoto())
                .postagemAT(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();
    }

    public static Foto toFotoUpdateRequest(FotoDTO dto) {
        return Foto.builder()
                .id(dto.getId())
                .strFoto(dto.getStrFoto())
                .postagemAT(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();
    }
}
