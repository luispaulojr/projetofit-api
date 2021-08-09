package br.com.senacrio.projetofitapi.domain.models;

import br.com.senacrio.projetofitapi.domain.enums.IngredienteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double quantidade;

    @Column(nullable = false)
    private IngredienteType tipo;

    @Column(nullable = false)
    private String complemento;
}
