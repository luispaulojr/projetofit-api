package br.com.senacrio.projetofitapi.domain.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class SerieDeExercicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Singular
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercicio_id", foreignKey = @ForeignKey(name = "fk_exercicio_id"))
    private List<Exercicio> exercicios;
}
