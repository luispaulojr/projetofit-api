package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.security.config.JwtTokenUtil;
import br.com.senacrio.projetofitapi.config.security.model.JwtRequest;
import br.com.senacrio.projetofitapi.config.security.model.JwtResponse;
import br.com.senacrio.projetofitapi.config.security.services.JwtUserDetailsService;
import br.com.senacrio.projetofitapi.domain.enums.UserStatus;
import br.com.senacrio.projetofitapi.gateway.repositories.UsuarioRepository;
import br.com.senacrio.projetofitapi.gateway.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Responsável por gerir a autenticação dos usuários")
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final UsuarioRepository repository;
    private final EmailService emailService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, UsuarioRepository repository, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.repository = repository;
        this.emailService = emailService;
    }

    @Operation(summary = "Autentica o usuário", responses = {
            @ApiResponse(description = "Sucesso ao autenticar o usuário", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(description = "Usuário não localizado", responseCode = "404", content = @Content),
            @ApiResponse(description = "Usuário não autenticado", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Operation(summary = "Ativa o usuário", responses = {
            @ApiResponse(description = "Sucesso ao ativar o usuário", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(description = "Usuário não localizado", responseCode = "404", content = @Content),
    })
    @RequestMapping(value = "/api/active", method = RequestMethod.GET)
    public ResponseEntity<?> activeUserWithToken(@RequestParam String token) throws Exception {
        return activeUser(token) ? ResponseEntity.ok("usuário ativado com sucesso") : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private boolean activeUser(String token) {
        var username = this.jwtTokenUtil.getUsernameFromToken(token);
        var user = this.userDetailsService.loadUserByUsernameInactive(username);

        if(this.jwtTokenUtil.validateToken(token,  user)){
            this.repository.activeUser(username);
            var usuario = this.repository.findByLogin(username, UserStatus.ATIVO);
            this.emailService.sendmailConfirmedUser(usuario);
            return true;
        }else {
            System.out.println("Token recebida: " + token + "\nUsername: " + user.getUsername() + "\ntoken expirada");
        }
        return false;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}