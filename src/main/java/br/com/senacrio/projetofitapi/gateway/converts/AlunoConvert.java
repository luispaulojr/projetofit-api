package br.com.senacrio.projetofitapi.gateway.converts;

import br.com.senacrio.projetofitapi.domain.dtos.AlunoDTO;
import br.com.senacrio.projetofitapi.domain.models.Aluno;

public class AlunoConvert {
    public static Aluno convertAlunoRequest(AlunoDTO dto) {
        return Aluno.builder()
                .nome(dto.getNome())
                .login(dto.getLogin())
                .senha(dto.getSenha())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .tipo(dto.getTipo())
                .status(dto.getStatus())
                .dataNascimento(dto.getDataNascimento())
                .altura(dto.getAltura())
                .peso(dto.getPeso())
                .circAbdominal(dto.getCircAbdominal())
                .build();
    }

    public static Aluno convertAlunoUpdateRequest(AlunoDTO dto) {
        return Aluno.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .login(dto.getLogin())
                .senha(dto.getSenha())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .tipo(dto.getTipo())
                .status(dto.getStatus())
                .dataNascimento(dto.getDataNascimento())
                .altura(dto.getAltura())
                .peso(dto.getPeso())
                .circAbdominal(dto.getCircAbdominal())
                .build();
    }
}
