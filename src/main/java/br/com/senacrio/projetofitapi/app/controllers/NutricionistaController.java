package br.com.senacrio.projetofitapi.app.controllers;

import br.com.senacrio.projetofitapi.config.ExceptionHandlers;
import br.com.senacrio.projetofitapi.config.utils.GeneratedSecuredPassword;
import br.com.senacrio.projetofitapi.domain.dtos.NutricionistaDTO;
import br.com.senacrio.projetofitapi.domain.models.Nutricionista;
import br.com.senacrio.projetofitapi.gateway.converters.NutricionistaConverter;
import br.com.senacrio.projetofitapi.gateway.repositories.NutricionistaRepository;
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

@Tag(name = "Nutricionista", description = "Responsável por controlar toda gestão de nutricionista")
@RestController
@RequestMapping("/api/nutricionista")
public class NutricionistaController {

    private final NutricionistaRepository repository;

    public NutricionistaController(NutricionistaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os nutricionistas", responses = {
            @ApiResponse(description = "Sucesso ao listar nutricionistas", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Nutricionista.class)))),
            @ApiResponse(description = "Nenhum nutricionista para exibir", responseCode = "404", content = @Content)
    })
    public ResponseEntity<List<Nutricionista>> getAllNutricionistas() {
        List<Nutricionista> nutricionistaList = (List<Nutricionista>) repository.findAll();
        if (nutricionistaList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nutricionistaList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Localiza um nutricionista pelo id", responses = {
            @ApiResponse(description = "Sucesso na busca do nutricionista", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Nutricionista.class))),
            @ApiResponse(description = "Nutricionista não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Nutricionista> getNutricionistaById(@PathVariable("id") Long id) {

        Optional<Nutricionista> nutricionista = repository.findById(id);
        return nutricionista.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Cadastra nutricionista", responses = {
            @ApiResponse(description = "Sucesso no cadastro do nutricionista", responseCode = "201", content = @Content),
            @ApiResponse(description = "Falha ao cadastrar o nutricionista", responseCode = "400", content = @Content),
            @ApiResponse(description = "Nutricionista já existente", responseCode = "409", content = @Content),
            @ApiResponse(description = "Falha do sistema", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Nutricionista> addNutricionista(@RequestBody @Valid NutricionistaDTO nutricionistaDTO) {

        nutricionistaDTO.setSenha(GeneratedSecuredPassword.hash(nutricionistaDTO.getSenha()));
        var nutricionista = NutricionistaConverter.toNutricionistaRequest(nutricionistaDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(NutricionistaController.class).slash(nutricionista.getId())
                .toUri());
        Nutricionista savedNutricionista;
        try {
            savedNutricionista = repository.save(nutricionista);
        } catch (Exception e) {
            return ExceptionHandlers.trataException(e.getMessage());
        }

        return new ResponseEntity<>(savedNutricionista, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um nutricionista pelo id", responses = {
            @ApiResponse(description = "Sucesso ao atualizar do nutricionista", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Nutricionista.class))),
            @ApiResponse(description = "Nutricionista não localizado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Nutricionista> updateNutricionista(@PathVariable("id") long id, @RequestBody NutricionistaDTO nutricionistaDTO) {
        boolean isNutricionistaPresent = repository.existsById(id);

        if (!isNutricionistaPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        nutricionistaDTO.setId(id);
        nutricionistaDTO.getConsultorio().setId(repository.findIdConsultorioByNutricionista(id));

        var updatedNutricionista = repository.save(NutricionistaConverter.toNutricionistaUpdateRequest(nutricionistaDTO));

        return new ResponseEntity<>(updatedNutricionista, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga nutricionista", responses = {
            @ApiResponse(description = "Sucesso ao excluir o nutricionista", responseCode = "204", content = @Content),
            @ApiResponse(description = "Falha ao excluir o nutricionista", responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> deleteNutricionista(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
