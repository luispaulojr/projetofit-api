package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.NutricionistaDTO;
import br.com.senacrio.projetofitapi.domain.models.Endereco;
import br.com.senacrio.projetofitapi.domain.models.Nutricionista;

public class NutricionistaConverter {

    public static Nutricionista toNutricionistaRequest(NutricionistaDTO dto) {

        var nutricionista = Nutricionista.builder()
                .nome(dto.getNome())
                .login(dto.getLogin())
                .senha(dto.getSenha())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .tipo(dto.getTipo())
                .status(dto.getStatus())
                .dataNascimento(dto.getDataNascimento())
                .crn(dto.getCrn())
                .endereco(
                        Endereco.builder()
                                .logradouro(dto.getConsultorio().getLogradouro())
                                .bairro(dto.getConsultorio().getBairro())
                                .cidade(dto.getConsultorio().getCidade())
                                .build())
                .build();
        System.out.println(nutricionista);

        return nutricionista;
    }

    public static Nutricionista toNutricionistaUpdateRequest(NutricionistaDTO dto) {

        var nutricionista = Nutricionista.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .login(dto.getLogin())
                .senha(dto.getSenha())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .tipo(dto.getTipo())
                .status(dto.getStatus())
                .dataNascimento(dto.getDataNascimento())
                .crn(dto.getCrn())
                .endereco(
                        Endereco.builder()
                                .id(dto.getConsultorio().getId())
                                .logradouro(dto.getConsultorio().getLogradouro())
                                .bairro(dto.getConsultorio().getBairro())
                                .cidade(dto.getConsultorio().getCidade())
                                .build())
                .build();
        System.out.println(nutricionista);

        return nutricionista;
    }
}
