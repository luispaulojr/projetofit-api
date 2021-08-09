package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.ExceptionHandlers;
import br.com.senacrio.projetofitapi.domain.dtos.AlunoDTO;
import br.com.senacrio.projetofitapi.domain.models.Aluno;
import br.com.senacrio.projetofitapi.gateway.converters.AlunoConverter;
import br.com.senacrio.projetofitapi.gateway.repositories.AlunoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Tag(name = "Aluno", description = "Responsável por controlar toda gestão de aluno")
@RestController
@RequestMapping("/api/aluno")
public class AlunoController {

    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os alunos", responses = {
            @ApiResponse(description = "Sucesso ao listar alunos", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Aluno.class)))),
            @ApiResponse(description = "Nenhum aluno para exibir", responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<Aluno>> getAllAlunos() {
        List<Aluno> alunoList = (List<Aluno>) repository.findAll();
        if (alunoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alunoList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Localiza um aluno pelo id", responses = {
            @ApiResponse(description = "Sucesso na busca do aluno", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(description = "Aluno não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Aluno> getAlunoById(@PathVariable("id") Long id) {

        Optional<Aluno> aluno = repository.findById(id);
        return aluno.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Cadastra aluno", responses = {
            @ApiResponse(description = "Sucesso no cadastro do aluno", responseCode = "201", content = @Content),
            @ApiResponse(description = "Falha ao cadastrar o aluno", responseCode = "400", content = @Content),
            @ApiResponse(description = "Aluno já existente", responseCode = "409", content = @Content),
            @ApiResponse(description = "Falha do sistema", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Aluno> addAluno(@RequestBody @Valid AlunoDTO alunoDTO) {
        var aluno = AlunoConverter.toAlunoRequest(alunoDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(AlunoController.class).slash(aluno.getId())
                .toUri());
        Aluno savedAluno;
        try {
            savedAluno = repository.save(aluno);
        } catch (Exception e) {
            return ExceptionHandlers.trataException(e.getMessage());
        }

        return new ResponseEntity<>(savedAluno, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um aluno pelo id", responses = {
            @ApiResponse(description = "Sucesso ao atualizar do aluno", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(description = "Aluno não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Aluno> updateAluno(@PathVariable("id") long id, @RequestBody AlunoDTO alunoDTO) {
        boolean isAlunoPresent = repository.existsById(id);

        if (!isAlunoPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        alunoDTO.setId(id);
        Aluno updatedAluno = repository.save(AlunoConverter.toAlunoUpdateRequest(alunoDTO));

        return new ResponseEntity<>(updatedAluno, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga aluno", responses = {
            @ApiResponse(description = "Sucesso ao excluir o aluno", responseCode = "204", content = @Content),
            @ApiResponse(description = "Falha ao excluir o aluno", responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> deleteAluno(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
