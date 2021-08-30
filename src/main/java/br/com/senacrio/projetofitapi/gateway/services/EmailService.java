package br.com.senacrio.projetofitapi.gateway.services;

import br.com.senacrio.projetofitapi.config.security.config.JwtTokenUtil;
import br.com.senacrio.projetofitapi.config.security.services.JwtUserDetailsService;
import br.com.senacrio.projetofitapi.domain.models.Usuario;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private final JavaMailSender emailSender;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    public EmailService(JavaMailSender emailSender, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.emailSender = emailSender;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    public void sendSimpleMessage(Usuario usuario) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@projetofit.com");
        message.setTo(usuario.getEmail());
        message.setSubject("Cadastro novo usuário");
        message.setText("Seja bem vindo ao Projetofit!!!\nPara continuar acesse: http://localhost/active?token=" + this.generateToken(usuario));
        emailSender.send(message);
        System.out.println("Email enviado para: " + usuario.getEmail());
    }

    private String generateToken(Usuario usuario) {
        var user = this.converterToUser(usuario);
        final UserDetails userDetails = userDetailsService.loadNewUser(usuario);
        return jwtTokenUtil.generateToken(userDetails);
    }

    private Usuario converterToUser(Usuario usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .login(usuarioDTO.getLogin())
                .email(usuarioDTO.getEmail()).build();
    }
}