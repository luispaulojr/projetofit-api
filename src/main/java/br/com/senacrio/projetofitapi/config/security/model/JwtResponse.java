package br.com.senacrio.projetofitapi.config.security.model;

import lombok.*;

@Data
public class JwtResponse {
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}