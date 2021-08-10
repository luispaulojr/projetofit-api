package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.ExceptionHandlers;
import br.com.senacrio.projetofitapi.domain.dtos.ExercicioDTO;
import br.com.senacrio.projetofitapi.domain.models.Exercicio;
import br.com.senacrio.projetofitapi.gateway.converters.ExercicioConverter;
import br.com.senacrio.projetofitapi.gateway.repositories.ExercicioRepository;
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

@Tag(name = "Exercicio", description = "Responsável por controlar toda gestão de exercicio")
@RestController
@RequestMapping("/api/exercicio")
public class ExercicioController {

    private final ExercicioRepository repository;

    public ExercicioController(ExercicioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os exercicios", responses = {
            @ApiResponse(description = "Sucesso ao listar exercicios", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exercicio.class)))),
            @ApiResponse(description = "Nenhum exercicio para exibir", responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<Exercicio>> getAllExercicios() {
        List<Exercicio> exercicioList = (List<Exercicio>) repository.findAll();
        if (exercicioList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(exercicioList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Localiza um exercicio pelo id", responses = {
            @ApiResponse(description = "Sucesso na busca do exercicio", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exercicio.class))),
            @ApiResponse(description = "Exercicio não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Exercicio> getExercicioById(@PathVariable("id") Long id) {

        Optional<Exercicio> exercicio = repository.findById(id);
        return exercicio.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Cadastra exercicio", responses = {
            @ApiResponse(description = "Sucesso no cadastro do exercicio", responseCode = "201", content = @Content),
            @ApiResponse(description = "Falha ao cadastrar o exercicio", responseCode = "400", content = @Content),
            @ApiResponse(description = "Exercicio já existente", responseCode = "409", content = @Content),
            @ApiResponse(description = "Falha do sistema", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Exercicio> addExercicio(@RequestBody @Valid ExercicioDTO exercicioDTO) {

        var exercicio = ExercicioConverter.toExercicioRequest(exercicioDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(ExercicioController.class).slash(exercicio.getId())
                .toUri());

        Exercicio savedExercicio;

        try {
            savedExercicio = repository.save(exercicio);
        } catch (Exception e) {
            return ExceptionHandlers.trataException(e.getMessage());
        }

        return new ResponseEntity<>(savedExercicio, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um exercicio pelo id", responses = {
            @ApiResponse(description = "Sucesso ao atualizar do exercicio", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exercicio.class))),
            @ApiResponse(description = "Exercicio não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Exercicio> updateExercicio(@PathVariable("id") long id, @RequestBody ExercicioDTO exercicioDTO) {
        boolean isExercicioPresent = repository.existsById(id);

        if (!isExercicioPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        exercicioDTO.setId(id);
        var updatedExercicio = repository.save(ExercicioConverter.toExercicioUpdateRequest(exercicioDTO));

        return new ResponseEntity<>(updatedExercicio, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga exercicio", responses = {
            @ApiResponse(description = "Sucesso ao excluir o exercicio", responseCode = "204", content = @Content),
            @ApiResponse(description = "Falha ao excluir o exercicio", responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> deleteExercicio(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
