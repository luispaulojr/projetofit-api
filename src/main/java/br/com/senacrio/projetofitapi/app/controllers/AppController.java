package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.gateway.repositories.AlunoRepository;
import br.com.senacrio.projetofitapi.gateway.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "Aluno", description = "Responsável por controlar toda gestão de aluno")
@RestController
@RequestMapping({"/"})
public class AppController {

    private final AlunoRepository repository;
    private final EmailService emailService;

    public AppController(AlunoRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @GetMapping
    @Operation(summary = "Encaminha para a documentação", responses = {
            @ApiResponse(description = "Sucesso ao listar alunos", responseCode = "302", content = @Content),
            @ApiResponse(description = "Nenhum conteúdo encontrado", responseCode = "404", content = @Content)
    })
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger");
    }
}
