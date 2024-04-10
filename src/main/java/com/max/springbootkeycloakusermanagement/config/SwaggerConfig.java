package com.max.springbootkeycloakusermanagement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${config.keycloak.accessTokenUri}")
    private String accessTokenUri;

    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title("MAX-JOBS - Jobs Request API")
                .version("1.0")
                //  .contact(contact)
                .description("MAX-JOBS - Jobs Request API");
        return new OpenAPI()
                .info(info)
                .components(new Components().addSecuritySchemes(
                                "OAuth2", new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .clientCredentials(new OAuthFlow().tokenUrl(accessTokenUri))
                                        )
                        )
                );

    }
}
