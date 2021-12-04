package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.ExceptionHandlers;
import br.com.senacrio.projetofitapi.domain.dtos.SerieDeExerciciosDTO;
import br.com.senacrio.projetofitapi.domain.models.Exercicio;
import br.com.senacrio.projetofitapi.domain.models.SerieDeExercicios;
import br.com.senacrio.projetofitapi.gateway.converters.SerieDeExerciciosConverter;
import br.com.senacrio.projetofitapi.gateway.repositories.ExercicioRepository;
import br.com.senacrio.projetofitapi.gateway.repositories.SerieDeExerciciosRepository;
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

@Tag(name = "Série de Exercícios", description = "Responsável por controlar toda gestão de série de exercícios")
@RestController
@RequestMapping("/api/serieDeExercicios")
public class SerieDeExerciciosController {

    private final SerieDeExerciciosRepository repository;
    private final ExercicioRepository exerciciosRepository;

    public SerieDeExerciciosController(SerieDeExerciciosRepository repository, ExercicioRepository exerciciosRepository) {
        this.repository = repository;
        this.exerciciosRepository = exerciciosRepository;
    }

    @GetMapping
    @Operation(summary = "Lista todos as séries de exercícios", responses = {
            @ApiResponse(description = "Sucesso ao listar série de exercícios", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SerieDeExercicios.class)))),
            @ApiResponse(description = "Nenhuma série de exercícios para exibir", responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<SerieDeExercicios>> getAllSerieDeExercicioss() {
        List<SerieDeExercicios> serieDeExerciciosList = (List<SerieDeExercicios>) repository.findAll();
        if (serieDeExerciciosList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(serieDeExerciciosList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Localiza um série de exercícios pelo id", responses = {
            @ApiResponse(description = "Sucesso na busca da série de exercícios", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SerieDeExercicios.class))),
            @ApiResponse(description = "série de exercícios não localizada", responseCode = "404", content = @Content)
    })
    public ResponseEntity<SerieDeExercicios> getSerieDeExerciciosById(@PathVariable("id") Long id) {

        Optional<SerieDeExercicios> serieDeExercicios = repository.findById(id);
        return serieDeExercicios.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Cadastra série de exercícios", responses = {
            @ApiResponse(description = "Sucesso no cadastro de uma série de exercícios", responseCode = "201", content = @Content),
            @ApiResponse(description = "Falha ao cadastrar a série de exercícios", responseCode = "400", content = @Content),
            @ApiResponse(description = "série de exercícios já existente", responseCode = "409", content = @Content),
            @ApiResponse(description = "Falha do sistema", responseCode = "500", content = @Content)
    })
    public ResponseEntity<SerieDeExercicios> addSerieDeExercicios(@RequestBody @Valid SerieDeExerciciosDTO serieDeExerciciosDTO) {

        var serieDeExercicios = SerieDeExerciciosConverter.toSerieDeExerciciosRequest(serieDeExerciciosDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(SerieDeExerciciosController.class).slash(serieDeExercicios.getId())
                .toUri());
        SerieDeExercicios savedSerieDeExercicios;
        try {
            savedSerieDeExercicios = repository.save(serieDeExercicios);
        } catch (Exception e) {
            return ExceptionHandlers.trataException(e.getMessage());
        }

        return new ResponseEntity<>(savedSerieDeExercicios, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{idSerie}/{idExercicio}")
    @Operation(summary = "Adiciona um exercício a série pelo id", responses = {
            @ApiResponse(description = "Sucesso ao vincular o exercício a série de exercícios", responseCode = "204", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SerieDeExercicios.class))),
            @ApiResponse(description = "Exercícios não vinculado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<SerieDeExercicios> addExercicioSerie(@PathVariable("idSerie") long idSerie, @PathVariable("idExercicio") long idExercicio) {

        boolean isSerieDeExerciciosPresent = repository.existsById(idSerie);
        if (!isSerieDeExerciciosPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean isExerciciosPresent = exerciciosRepository.existsById(idExercicio);
        if (!isExerciciosPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var serieDeExercicios = repository.findById(idSerie).get();
        serieDeExercicios.getExercicios().add(Exercicio.builder().id(idExercicio).build());

        var updatedSerieDeExercicios = repository.save(serieDeExercicios);

        return new ResponseEntity<>(updatedSerieDeExercicios, HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um série de exercícios pelo id", responses = {
            @ApiResponse(description = "Sucesso ao atualizar a série de exercícios", responseCode = "204", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SerieDeExercicios.class))),
            @ApiResponse(description = "série de exercícios não localizada", responseCode = "404", content = @Content)
    })
    public ResponseEntity<SerieDeExercicios> updateSerieDeExercicios(@PathVariable("id") long id, @RequestBody SerieDeExerciciosDTO serieDeExerciciosDTO) {
        boolean isSerieDeExerciciosPresent = repository.existsById(id);

        if (!isSerieDeExerciciosPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        serieDeExerciciosDTO.setId(id);

        var updatedSerieDeExercicios = repository.save(SerieDeExerciciosConverter.toSerieDeExerciciosUpdateRequest(serieDeExerciciosDTO));

        return new ResponseEntity<>(updatedSerieDeExercicios, HttpStatus.NO_CONTENT);
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga série de exercícios", responses = {
            @ApiResponse(description = "Sucesso ao excluir a série de exercícios", responseCode = "204", content = @Content),
            @ApiResponse(description = "Falha ao excluir o série de exercícios", responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> deleteSerieDeExercicios(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
