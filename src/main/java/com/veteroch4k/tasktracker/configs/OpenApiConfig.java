package com.veteroch4k.tasktracker.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
    info = @Info(
        title = "Task time tracker API",
        description = "API сервиса учета рабочего времени сотрудников",
        version = "1.0",
        contact = @Contact(
            name = "Popov Victor",
            email = "veteroch4k@gmail.com",
            url = "https://github.com/Veteroch4k"
        )
    ),
    security = {
        @SecurityRequirement(name = "bearerAuth")
    }

)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Скопируйте и вставьте сюда ваш JWT токен (без слова Bearer)"
)
public class OpenApiConfig {


}
