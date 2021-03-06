package br.com.senacrio.projetofitapi.domain.dtos;

import br.com.senacrio.projetofitapi.domain.enums.GeneroType;
import br.com.senacrio.projetofitapi.domain.enums.UserStatus;
import br.com.senacrio.projetofitapi.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@ToString
@Data
public class ProfessorDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotBlank(message = "Valor é obrigatório!")
    private String nome;

    @Enumerated(EnumType.STRING)
    private GeneroType genero;

    @NotBlank(message = "Valor é obrigatório!")
    private String login;

    @NotBlank(message = "Valor é obrigatório!")
    private String senha;

    @NotBlank(message = "Valor é obrigatório!")
    private String email;

    @NotBlank(message = "Valor é obrigatório!")
    private String telefone;

    @Enumerated(EnumType.STRING)
    private UserType tipo = UserType.PROFESSOR;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.INATIVO;

    @Past(message = "A data não pode ser corrente ou futura")
    @NotNull(message = "Valor é obrigatório!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotBlank(message = "Valor é obrigatório!")
    private String cref;

    @NotNull(message = "Valor é obrigatório!")
    private AcademiaDTO academiaFiliada;
}
