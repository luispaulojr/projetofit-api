package br.com.senacrio.projetofitapi.domain.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "aluno_id", foreignKey = @ForeignKey(name = "fk_aluno_id"))
    private Aluno aluno;

    @OneToOne
    @JoinColumn(name = "professor_id", foreignKey = @ForeignKey(name = "fk_professor_id"))
    private Professor professor;


    @Column(nullable = false)
    @Singular("SerieDeExercicios")
    @OneToMany
    @JoinColumn(name = "serieDeExerciciosList_id", foreignKey = @ForeignKey(name = "fk_serie_de_exercicios_id"))
    private List<SerieDeExercicios> serieDeExerciciosList;
}
