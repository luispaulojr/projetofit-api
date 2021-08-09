package br.com.senacrio.projetofitapi.domain.models;

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
public class Academia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;

    @Column(unique = true, nullable = false)
    private String cpnj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", foreignKey = @ForeignKey(name = "fk_endereco_id"))
    private Endereco endereco;
}
