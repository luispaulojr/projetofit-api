package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.ExceptionHandlers;
import br.com.senacrio.projetofitapi.domain.dtos.TreinoDTO;
import br.com.senacrio.projetofitapi.domain.models.Treino;
import br.com.senacrio.projetofitapi.gateway.converters.TreinoConverter;
import br.com.senacrio.projetofitapi.gateway.repositories.TreinoRepository;
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

@Tag(name = "Treino", description = "Responsável por controlar toda gestão de treino")
@RestController
@RequestMapping("/api/treino")
public class TreinoController {

    private final TreinoRepository repository;

    public TreinoController(TreinoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os treinos", responses = {
            @ApiResponse(description = "Sucesso ao listar treinos", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Treino.class)))),
            @ApiResponse(description = "Nenhum treino para exibir", responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<Treino>> getAllTreinos() {
        List<Treino> treinoList = (List<Treino>) repository.findAll();
        if (treinoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(treinoList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Localiza um treino pelo id", responses = {
            @ApiResponse(description = "Sucesso na busca do treino", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Treino.class))),
            @ApiResponse(description = "Treino não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Treino> getTreinoById(@PathVariable("id") Long id) {

        Optional<Treino> treino = repository.findById(id);
        return treino.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Cadastra treino", responses = {
            @ApiResponse(description = "Sucesso no cadastro do treino", responseCode = "201", content = @Content),
            @ApiResponse(description = "Falha ao cadastrar o treino", responseCode = "400", content = @Content),
            @ApiResponse(description = "Treino já existente", responseCode = "409", content = @Content),
            @ApiResponse(description = "Falha do sistema", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Treino> addTreino(@RequestBody @Valid TreinoDTO treinoDTO) {

        var treino = TreinoConverter.toTreinoRequest(treinoDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(TreinoController.class).slash(treino.getId())
                .toUri());

        Treino savedTreino;

        try {
            savedTreino = repository.save(treino);
        } catch (Exception e) {
            return ExceptionHandlers.trataException(e.getMessage());
        }

        return new ResponseEntity<>(savedTreino, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um treino pelo id", responses = {
            @ApiResponse(description = "Sucesso ao atualizar do treino", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Treino.class))),
            @ApiResponse(description = "Treino não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Treino> updateTreino(@PathVariable("id") long id, @RequestBody TreinoDTO treinoDTO) {
        boolean isTreinoPresent = repository.existsById(id);

        if (!isTreinoPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        treinoDTO.setId(id);
        var updatedTreino = repository.save(TreinoConverter.toTreinoUpdateRequest(treinoDTO));

        return new ResponseEntity<>(updatedTreino, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga treino", responses = {
            @ApiResponse(description = "Sucesso ao excluir o treino", responseCode = "204", content = @Content),
            @ApiResponse(description = "Falha ao excluir o treino", responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> deleteTreino(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
