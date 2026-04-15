package com.veteroch4k.tasktracker.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

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
    )
)
public class OpenApiConfig {

}
