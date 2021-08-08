package br.com.senacrio.projetofitapi.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "consultorio")
    private Nutricionista nutricionista;

    @NonNull
    @NotBlank(message = "Valor é obrigatório!")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String logradouro;

    @NonNull
    @NotBlank(message = "Valor é obrigatório!")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String bairro;

    @NonNull
    @NotBlank(message = "Valor é obrigatório!")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String cidade;
}
