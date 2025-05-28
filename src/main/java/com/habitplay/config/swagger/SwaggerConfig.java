package com.habitplay.config.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("com.habitplay")  // Adjust if your controllers are in a different package
                .pathsToMatch("/api/**")  // Optional, to only include specific paths
                .build();
    }
}
