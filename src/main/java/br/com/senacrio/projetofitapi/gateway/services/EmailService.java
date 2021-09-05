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

    public void sendmailConfirmUser(Usuario usuario) {
        var subject = "Cadastro novo usuário";
        var text = "Seja bem vindo ao Projetofit!!!\nPara continuar acesse: http://localhost/api/active?token=" + this.generateToken(usuario);

        this.sendSimpleMessage(usuario.getEmail(), subject, text);
    }

    public void sendmailConfirmedUser(Usuario usuario) {
        var subject = "Ativação de usuário";
        var text = "Usuário ativado com sucesso!\nAgora você pode desfrutar dos recursos desse excelente app\n\nSeja bem vindo ao Projetofit!!!\n";

        sendSimpleMessage(usuario.getEmail(), subject, text);
        System.out.println("Email enviado para: " + usuario.getEmail());
    }

    private void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@projetofit.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        System.out.println("Email enviado para: " + to);
    }


    private String generateToken(Usuario usuario) {
        var user = this.converterToUser(usuario);
        final UserDetails userDetails = userDetailsService.loadNewUser(usuario);
        return jwtTokenUtil.generateToken(userDetails, usuario.getTipo());
    }

    private Usuario converterToUser(Usuario usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .login(usuarioDTO.getLogin())
                .email(usuarioDTO.getEmail()).build();
    }
}