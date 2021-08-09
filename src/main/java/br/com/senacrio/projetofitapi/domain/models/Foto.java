package br.com.senacrio.projetofitapi.domain.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String strFoto;

    @Column(name = "postagem_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime postagemAT;
}
