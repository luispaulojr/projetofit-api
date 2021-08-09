package br.com.senacrio.projetofitapi.domain.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@ToString(callSuper=true, includeFieldNames=true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@PrimaryKeyJoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario_id"))
public class Professor extends Usuario {

    private String cref;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "academia_id", foreignKey = @ForeignKey(name = "fk_academia_id"))
    private Academia academiaFiliada;
}
