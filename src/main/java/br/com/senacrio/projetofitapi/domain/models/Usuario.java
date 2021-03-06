package br.com.senacrio.projetofitapi.domain.models;

import br.com.senacrio.projetofitapi.domain.enums.GeneroType;
import br.com.senacrio.projetofitapi.domain.enums.UserStatus;
import br.com.senacrio.projetofitapi.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
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

    @Column
    private String nome;

    @Column
    private GeneroType genero;

    @Column(unique = true)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Email fora do Padrao")
    private String email;

    private String telefone;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType tipo = UserType.ALUNO;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.INATIVO;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
