package br.com.senacrio.projetofitapi.config.security.model;

import lombok.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtRequest {
    private String username;
    private String password;
}