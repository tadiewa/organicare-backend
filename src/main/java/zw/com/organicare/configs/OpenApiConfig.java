/**
 * @author : tadiewa
 * date: 9/10/2025
 */


package zw.com.organicare.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "OrganiCare API",
                version = "1.0",
                description = "OrganiCare REST API documentation",
                contact = @Contact(
                        name = "OrganiCare Team",
                        email = "OrganiCare@org.com"
                )
        ),
        security = @SecurityRequirement(name = "Bearer Authentication"),
        servers = {
                @Server(
                        description = "Local Environment",
                      url = "http://localhost:9033"
                       // url = "https://organicare.onrender.com"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "JWT Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
