package br.com.senacrio.projetofitapi.domain.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String objetivo;

    @Column(nullable = false)
    @Singular
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingrediente_id", foreignKey = @ForeignKey(name = "fk_ingrediente_id"))
    private List<Ingrediente> ingredientes;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String preparo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nutricionista_id", foreignKey = @ForeignKey(name = "fk_nutricionista_id"))
    private Nutricionista nutricionista;
}
