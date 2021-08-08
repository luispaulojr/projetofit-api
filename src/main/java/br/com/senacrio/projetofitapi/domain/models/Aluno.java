package br.com.senacrio.projetofitapi.domain.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Entity
public class Aluno extends Usuario implements Serializable {

    private Double altura;
    private Double peso;
    private Double circAbdominal;
}
