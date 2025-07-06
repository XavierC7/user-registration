package com.assessment.registration.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "User Registration API",
        version = "1.0",
        description = "API para el registro de usuarios - EY Evaluation"
    )
)
public class OpenApiConfig {

}
