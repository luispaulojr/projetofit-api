package br.com.senacrio.projetofitapi.domain.models;

import br.com.senacrio.projetofitapi.domain.enums.SerieDeExerciciosStatus;
import br.com.senacrio.projetofitapi.domain.enums.UserType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private SerieDeExerciciosStatus status = SerieDeExerciciosStatus.INATIVA;

    @Singular
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercicio_id", foreignKey = @ForeignKey(name = "fk_exercicio_id"))
    private List<Exercicio> exercicios;

    @OneToOne
    @JoinColumn(name = "professor_id", foreignKey = @ForeignKey(name = "fk_professor_id"))
    private Professor professor;
}
