package br.com.senacrio.projetofitapi.domain.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Nutricionista extends Usuario implements Serializable {

    @NonNull
    @NotBlank(message = "Valor é obrigatório!")
    private String crn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consultorio_id", referencedColumnName = "id")
    private Endereco consultorio;
}
