package br.com.senacrio.projetofitapi.domain.models;

import br.com.senacrio.projetofitapi.domain.enums.UnidadeMedidaType;
import lombok.*;

import javax.persistence.*;

@ToString
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
    private UnidadeMedidaType tipo;

    @Column(nullable = false)
    private String complemento;
}
