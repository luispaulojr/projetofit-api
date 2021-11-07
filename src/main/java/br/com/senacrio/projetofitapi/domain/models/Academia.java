package br.com.senacrio.projetofitapi.domain.models;

import lombok.*;

import javax.persistence.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Academia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", foreignKey = @ForeignKey(name = "fk_endereco_id"))
    private Endereco endereco;
}
