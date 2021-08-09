package br.com.senacrio.projetofitapi.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.senacrio.projetofitapi.ProjetofitApiApplication;
import br.com.senacrio.projetofitapi.domain.dtos.AlunoDTO;
import br.com.senacrio.projetofitapi.domain.enums.UserStatus;
import br.com.senacrio.projetofitapi.domain.enums.UserType;
import br.com.senacrio.projetofitapi.gateway.repositories.AlunoRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ProjetofitApiApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class AlunoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AlunoRepository repository;

    private List<AlunoDTO> alunoList;

    @BeforeEach
    void setUp() {
        this.alunoList = new ArrayList<>();
        this.alunoList.add(AlunoDTO.builder()
                .nome("Luis")
                .login("lpjunior")
                .email("luis@gmail.com")
                .senha("123qwe.")
                .telefone("(21) 96354-1287")
                .tipo(UserType.ALUNO)
                .status(UserStatus.ATIVO)
                .dataNascimento(LocalDate.parse("1987-04-29"))
                .altura(1.82)
                .peso(95.0)
                .circAbdominal(55.3)
                .build());
    }

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void testGetAllAlunos() throws Exception {
        mvc.perform(get("/api/aluno").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].nome", is("Luis")));
    }

    @Test
    void testGetAlunoById() {
    }

    @Test
    void testAddAluno() {
    }

    @Test
    void testUpdateAluno() {
    }

    @Test
    void testDeleteAluno() {
    }
}