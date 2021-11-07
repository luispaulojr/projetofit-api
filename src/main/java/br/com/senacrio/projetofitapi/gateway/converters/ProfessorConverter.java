package br.com.senacrio.projetofitapi.gateway.converters;

import br.com.senacrio.projetofitapi.domain.dtos.ProfessorDTO;
import br.com.senacrio.projetofitapi.domain.models.Academia;
import br.com.senacrio.projetofitapi.domain.models.Endereco;
import br.com.senacrio.projetofitapi.domain.models.Professor;

public class ProfessorConverter {
    public static Professor toProfessorRequest(ProfessorDTO dto) {

        return Professor.builder()
                .nome(dto.getNome())
                .genero(dto.getGenero())
                .login(dto.getLogin())
                .senha(dto.getSenha())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .tipo(dto.getTipo())
                .status(dto.getStatus())
                .dataNascimento(dto.getDataNascimento())
                .cref(dto.getCref())
                .academiaFiliada(Academia.builder()
                        .razaoSocial(dto.getAcademiaFiliada().getRazaoSocial())
                        .cnpj(dto.getAcademiaFiliada().getCnpj())
                        .endereco(Endereco.builder()
                                .logradouro(dto.getAcademiaFiliada().getEndereco().getLogradouro())
                                .bairro(dto.getAcademiaFiliada().getEndereco().getBairro())
                                .cidade(dto.getAcademiaFiliada().getEndereco().getCidade())
                                .build())
                        .build())
                .build();
    }

    public static Professor toProfessorUpdateRequest(ProfessorDTO dto) {

        return Professor.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .genero(dto.getGenero())
                .login(dto.getLogin())
                .senha(dto.getSenha())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .tipo(dto.getTipo())
                .status(dto.getStatus())
                .dataNascimento(dto.getDataNascimento())
                .cref(dto.getCref())
                .academiaFiliada(Academia.builder()
                        .id(dto.getAcademiaFiliada().getId())
                        .razaoSocial(dto.getAcademiaFiliada().getRazaoSocial())
                        .cnpj(dto.getAcademiaFiliada().getCnpj())
                        .endereco(Endereco.builder()
                                .id(dto.getAcademiaFiliada().getEndereco().getId())
                                .logradouro(dto.getAcademiaFiliada().getEndereco().getLogradouro())
                                .bairro(dto.getAcademiaFiliada().getEndereco().getBairro())
                                .cidade(dto.getAcademiaFiliada().getEndereco().getCidade())
                                .build())
                        .build())
                .build();
    }
}
