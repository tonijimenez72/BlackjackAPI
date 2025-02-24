package cat.itacademy.s05.t01.n01.blackjackapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("Blackjack API")
                        .version("1.0")
                        .description("IT Academy - Sprint 5.1 Advanced Spring Framework with WebFlux documentation"));
    }
}
