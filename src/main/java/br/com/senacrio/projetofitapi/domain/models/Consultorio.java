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

    @OneToOne
    @JoinColumn(name = "aluno_id", foreignKey = @ForeignKey(name = "fk_aluno_id"))
    private Aluno aluno;

    @OneToOne
    @JoinColumn(name = "nutricionista_id", foreignKey = @ForeignKey(name = "fk_nutricionista_id"))
    private Nutricionista nutricionista;


    @Column(nullable = false)
    @Singular()
    @OneToMany
    @JoinColumn(name = "Receita_id", foreignKey = @ForeignKey(name = "fk_receita_id"))
    private List<Receita> receitas;
}
