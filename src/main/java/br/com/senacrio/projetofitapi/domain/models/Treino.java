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

    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "nutricionista_id", foreignKey = @ForeignKey(name = "fk_aluno_id"))
    private Aluno aluno;

    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "professor_id", foreignKey = @ForeignKey(name = "fk_professor_id"))
    private Professor professor;


    @Column(nullable = false)
    @Singular("SerieDeExercicios")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Receita_id", foreignKey = @ForeignKey(name = "fk_Receita_id"))
    private List<Receita> receitas;
}
