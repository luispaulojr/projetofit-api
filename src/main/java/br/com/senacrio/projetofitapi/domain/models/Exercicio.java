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
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String musculo;
    private String repeticoes;

    @Singular
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "foto_id", foreignKey = @ForeignKey(name = "fk_foto_id"))
    private List<Foto> fotos;
}
