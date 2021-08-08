package br.com.senacrio.projetofitapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjetofitApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetofitApiApplication.class, args);
    }

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI().info(apiinfo());
    }

    private Info apiinfo() {
        return new Info().title("Projetofit-api")
                .description("Projeto para conclusão de curso da pós graduação de Engenharia de Software do Senac Rio")
                .version("v1.0.0");
    }
}
