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
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "nutricionista_id", foreignKey = @ForeignKey(name = "fk_aluno_id"))
    private Aluno aluno;

    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "nutricionista_id", foreignKey = @ForeignKey(name = "fk_nutricionista_id"))
    private Nutricionista nutricionista;


    @Column(nullable = false)
    @Singular("SerieDeExercicios")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingrediente_id", foreignKey = @ForeignKey(name = "fk_serieDeExercicios_id"))
    private List<SerieDeExercicios> serieDeExerciciosList;
}
