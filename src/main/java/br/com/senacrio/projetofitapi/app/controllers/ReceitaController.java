package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.ExceptionHandlers;
import br.com.senacrio.projetofitapi.domain.dtos.ReceitaDTO;
import br.com.senacrio.projetofitapi.domain.models.Receita;
import br.com.senacrio.projetofitapi.gateway.converters.ReceitaConverter;
import br.com.senacrio.projetofitapi.gateway.repositories.ReceitaRepository;
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

@Tag(name = "Receita", description = "Responsável por controlar toda gestão de receita")
@RestController
@RequestMapping("/api/receita")
public class ReceitaController {

    private final ReceitaRepository repository;

    public ReceitaController(ReceitaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os receitas", responses = {
            @ApiResponse(description = "Sucesso ao listar as receitas", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Receita.class)))),
            @ApiResponse(description = "Nenhuma receita para exibir", responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<Receita>> getAllReceitas() {
        List<Receita> receitaList = (List<Receita>) repository.findAll();
        if (receitaList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(receitaList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Localiza uma receita pelo id", responses = {
            @ApiResponse(description = "Sucesso na busca da receita", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Receita.class))),
            @ApiResponse(description = "Receita não localizada", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Receita> getReceitaById(@PathVariable("id") Long id) {

        Optional<Receita> receita = repository.findById(id);

        return receita.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Cadastra receita", responses = {
            @ApiResponse(description = "Sucesso no cadastro da receita", responseCode = "201", content = @Content),
            @ApiResponse(description = "Falha ao cadastrar a receita", responseCode = "400", content = @Content),
            @ApiResponse(description = "Receita já existente", responseCode = "409", content = @Content),
            @ApiResponse(description = "Falha do sistema", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Receita> addReceita(@RequestBody @Valid ReceitaDTO receitaDTO) {

        var receita = ReceitaConverter.toReceitaRequest(receitaDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(ReceitaController.class).slash(receita.getId())
                .toUri());
        Receita savedReceita;
        try {
            savedReceita = repository.save(receita);
        } catch (Exception e) {
            return ExceptionHandlers.trataException(e.getMessage());
        }

        return new ResponseEntity<>(savedReceita, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma receita pelo id", responses = {
            @ApiResponse(description = "Sucesso ao atualizar da receita", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Receita.class))),
            @ApiResponse(description = "Receita não localizada", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Receita> updateReceita(@PathVariable("id") long id, @RequestBody ReceitaDTO receitaDTO) {
        boolean isReceitaPresent = repository.existsById(id);

        if (!isReceitaPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        receitaDTO.setId(id);

        Receita updatedReceita = repository.save(ReceitaConverter.toReceitaUpdateRequest(receitaDTO));

        return new ResponseEntity<>(updatedReceita, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga receita", responses = {
            @ApiResponse(description = "Sucesso ao excluir a receita", responseCode = "204", content = @Content),
            @ApiResponse(description = "Falha ao excluir a receita", responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> deleteReceita(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
