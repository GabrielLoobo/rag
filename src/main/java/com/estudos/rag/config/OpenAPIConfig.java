package com.estudos.rag.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import io.swagger.v3.oas.models.info.Info;

@Profile("!test")
@Configuration
public class OpenAPIConfig {
  @Value("${app.version}")
  private String version;

  @Bean
  public GroupedOpenApi openApi() {
    return GroupedOpenApi.builder()
        .group("api")
        .packagesToScan("com.estudos.rag")
        .addOpenApiCustomizer(
            openApi -> openApi.info(
                new Info()
                    .title("RAG - API")
                    .version(version)))
        .build();
  }
}
