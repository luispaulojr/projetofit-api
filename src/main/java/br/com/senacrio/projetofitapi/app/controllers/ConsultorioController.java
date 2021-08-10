package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.ExceptionHandlers;
import br.com.senacrio.projetofitapi.domain.dtos.ConsultorioDTO;
import br.com.senacrio.projetofitapi.domain.models.Consultorio;
import br.com.senacrio.projetofitapi.gateway.converters.ConsultorioConverter;
import br.com.senacrio.projetofitapi.gateway.repositories.ConsultorioRepository;
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

@Tag(name = "Consultório", description = "Responsável por controlar toda gestão de consultório")
@RestController
@RequestMapping("/api/consultorio")
public class ConsultorioController {

    private final ConsultorioRepository repository;

    public ConsultorioController(ConsultorioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os consultórios", responses = {
            @ApiResponse(description = "Sucesso ao listar consultorios", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Consultorio.class)))),
            @ApiResponse(description = "Nenhum consultório para exibir", responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<Consultorio>> getAllConsultorios() {
        List<Consultorio> consultorioList = (List<Consultorio>) repository.findAll();
        if (consultorioList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(consultorioList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Localiza um consultório pelo id", responses = {
            @ApiResponse(description = "Sucesso na busca do consultório", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Consultorio.class))),
            @ApiResponse(description = "consultório não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Consultorio> getConsultorioById(@PathVariable("id") Long id) {

        Optional<Consultorio> consultorio = repository.findById(id);
        return consultorio.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Cadastra consultório", responses = {
            @ApiResponse(description = "Sucesso no cadastro do consultório", responseCode = "201", content = @Content),
            @ApiResponse(description = "Falha ao cadastrar o consultório", responseCode = "400", content = @Content),
            @ApiResponse(description = "consultório já existente", responseCode = "409", content = @Content),
            @ApiResponse(description = "Falha do sistema", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Consultorio> addConsultorio(@RequestBody @Valid ConsultorioDTO consultorioDTO) {

        var consultorio = ConsultorioConverter.toConsultorioRequest(consultorioDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(ConsultorioController.class).slash(consultorio.getId())
                .toUri());

        Consultorio savedConsultorio;

        try {
            savedConsultorio = repository.save(consultorio);
        } catch (Exception e) {
            return ExceptionHandlers.trataException(e.getMessage());
        }

        return new ResponseEntity<>(savedConsultorio, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um consultório pelo id", responses = {
            @ApiResponse(description = "Sucesso ao atualizar do consultório", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Consultorio.class))),
            @ApiResponse(description = "consultório não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Consultorio> updateConsultorio(@PathVariable("id") long id, @RequestBody ConsultorioDTO consultorioDTO) {
        boolean isConsultorioPresent = repository.existsById(id);

        if (!isConsultorioPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        consultorioDTO.setId(id);
        var updatedConsultorio = repository.save(ConsultorioConverter.toConsultorioUpdateRequest(consultorioDTO));

        return new ResponseEntity<>(updatedConsultorio, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga consultório", responses = {
            @ApiResponse(description = "Sucesso ao excluir o consultório", responseCode = "204", content = @Content),
            @ApiResponse(description = "Falha ao excluir o consultório", responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> deleteConsultorio(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
