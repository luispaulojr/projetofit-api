package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.ExceptionHandlers;
import br.com.senacrio.projetofitapi.domain.dtos.ProfessorDTO;
import br.com.senacrio.projetofitapi.domain.models.Academia;
import br.com.senacrio.projetofitapi.domain.models.Endereco;
import br.com.senacrio.projetofitapi.domain.models.Professor;
import br.com.senacrio.projetofitapi.gateway.repositories.ProfessorRepository;
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

@Tag(name = "Professor", description = "Responsável por controlar toda gestão de professor")
@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    private final ProfessorRepository repository;

    public ProfessorController(ProfessorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os professors", responses = {
            @ApiResponse(description = "Sucesso ao listar professors", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Professor.class)))),
            @ApiResponse(description = "Nenhum professor para exibir", responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<Professor>> getAllProfessors() {
        List<Professor> professorList = (List<Professor>) repository.findAll();
        if (professorList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(professorList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Localiza um professor pelo id", responses = {
            @ApiResponse(description = "Sucesso na busca do professor", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class))),
            @ApiResponse(description = "Professor não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Professor> getProfessorById(@PathVariable("id") Long id) {

        Optional<Professor> professor = repository.findById(id);
        return professor.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Cadastra professor", responses = {
            @ApiResponse(description = "Sucesso no cadastro do professor", responseCode = "201", content = @Content),
            @ApiResponse(description = "Falha ao cadastrar o professor", responseCode = "400", content = @Content),
            @ApiResponse(description = "Professor já existente", responseCode = "409", content = @Content),
            @ApiResponse(description = "Falha do sistema", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Professor> addProfessor(@RequestBody @Valid ProfessorDTO professorDTO) {
        var professor = convertProfessor(professorDTO, Optional.empty());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(ProfessorController.class).slash(professor.getId())
                .toUri());
        Professor savedProfessor;
        try {
            savedProfessor = repository.save(professor);
        } catch (Exception e) {
            return ExceptionHandlers.trataException(e.getMessage());
        }

        return new ResponseEntity<>(savedProfessor, httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cadastra professor", responses = {
            @ApiResponse(description = "Sucesso ao excluir o professor", responseCode = "204", content = @Content),
            @ApiResponse(description = "Falha ao excluir o professor", responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> deleteProfessor(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um professor pelo id", responses = {
            @ApiResponse(description = "Sucesso ao atualizar do professor", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class))),
            @ApiResponse(description = "Professor não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Professor> updateProfessor(@PathVariable("id") long id, @RequestBody ProfessorDTO professorDTO) {
        boolean isProfessorPresent = repository.existsById(id);

        if (!isProfessorPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Professor updatedProfessor = repository.save(convertProfessor(professorDTO, Optional.of(id)));

        return new ResponseEntity<>(updatedProfessor, HttpStatus.OK);
    }

    private Professor convertProfessor(ProfessorDTO professorDTO, Optional<Long> id) {
        var academia = (id.isPresent() ? repository.findAcademiaByProfessor(id.get()) : null);

        return Professor.builder()
                .id(id.orElse(null))
                .nome(professorDTO.getNome())
                .login(professorDTO.getLogin())
                .senha(professorDTO.getSenha())
                .email(professorDTO.getEmail())
                .telefone(professorDTO.getTelefone())
                .tipo(professorDTO.getTipo())
                .status(professorDTO.getStatus())
                .dataNascimento(professorDTO.getDataNascimento())
                .cref(professorDTO.getCref())
                .academiaFiliada(Academia.builder()
                        .id((id.isPresent() ? academia.getId() : null))
                        .razaoSocial(professorDTO.getAcademiaFiliada().getRazaoSocial())
                        .cpnj(professorDTO.getAcademiaFiliada().getCpnj())
                        .endereco(Endereco.builder()
                                .id((id.isPresent() ? academia.getEndereco().getId() : null))
                                .logradouro(professorDTO.getAcademiaFiliada().getEndereco().getLogradouro())
                                .bairro(professorDTO.getAcademiaFiliada().getEndereco().getBairro())
                                .cidade(professorDTO.getAcademiaFiliada().getEndereco().getCidade())
                                .build())
                        .build())
                .build();
    }
}
