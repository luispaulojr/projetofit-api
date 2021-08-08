package br.com.senacrio.projetofitapi.domain.models;

import br.com.senacrio.projetofitapi.domain.enums.UserStatus;
import br.com.senacrio.projetofitapi.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Valor é obrigatório!")
    @JsonInclude(Include.NON_NULL)
    @Column
    private String nome;

    @NonNull
    @NotBlank(message = "Valor é obrigatório!")
    @JsonInclude(Include.NON_NULL)
    @Column(unique = true)
    private String login;

    @NonNull
    @NotBlank(message = "Valor é obrigatório!")
    @JsonInclude(Include.NON_NULL)
    @ColumnTransformer(read = "md5(senha)", write = "md5(?)")
    private String senha;

    @NonNull
    @JsonInclude(Include.NON_NULL)
    @Column(unique = true)
    @NotBlank(message = "Valor é obrigatório!")
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Email fora do Padrao")
    private String email;

    @NonNull
    @JsonInclude(Include.NON_NULL)
    @NotBlank(message = "Valor é obrigatório!")
    @Column(unique = true)
    private String telefone;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType tipo = UserType.ALUNO;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.INATIVO;

    @NonNull
    @JsonInclude(Include.NON_NULL)
    @NotNull(message = "Valor é obrigatório!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
}
