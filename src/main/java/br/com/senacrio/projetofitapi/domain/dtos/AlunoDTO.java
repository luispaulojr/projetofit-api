package br.com.senacrio.projetofitapi.domain.dtos;

import br.com.senacrio.projetofitapi.domain.enums.UserStatus;
import br.com.senacrio.projetofitapi.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Builder
@ToString
@Data
public class AlunoDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotBlank(message = "Valor é obrigatório!")
    private String nome;

    @NotBlank(message = "Valor é obrigatório!")
    private String login;

    @NotBlank(message = "Valor é obrigatório!")
    private String senha;

    @NotBlank(message = "Valor é obrigatório!")
    private String email;

    @NotBlank(message = "Valor é obrigatório!")
    private String telefone;

    @Enumerated(EnumType.STRING)
    private UserType tipo;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Past
    @NotNull(message = "Valor é obrigatório!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    private Double altura;

    private Double peso;

    private Double circAbdominal;
}
