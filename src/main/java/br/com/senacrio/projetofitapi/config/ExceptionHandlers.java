package br.com.senacrio.projetofitapi.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlers {
    public static ResponseEntity trataException(String errorMessage) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        if (errorMessage.contains("ConstraintViolationException"))
            httpStatus = HttpStatus.CONFLICT;

        return new ResponseEntity<>(httpStatus);
    }
}
