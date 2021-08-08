package br.com.senacrio.projetofitapi.domain.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Professor extends Usuario implements Serializable {

    private String cref;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "academia_id")
    private Academia academiaFiliada;
}
