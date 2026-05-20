package az.edu.ada.wm2.courseservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Course Service API",
                version = "v1",
                description = "CRUD and enrollment API for course management.",
                contact = @Contact(name = "WM2 Backend Course"),
                license = @License(name = "Educational Use")
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "Local server")
        }
)
public class OpenApiConfig {
}
