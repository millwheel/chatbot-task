package com.example.chatbot.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(servers = [Server(url = "/", description = "Default Server url")])
class SwaggerConfig {

    @Bean
    fun springShopOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("chatbot api")
                    .description("chatbot api")
                    .version("v0.0.1")
                    .license(License().name("Apache 2.0").url("http://springdoc.org"))
            )
    }
}