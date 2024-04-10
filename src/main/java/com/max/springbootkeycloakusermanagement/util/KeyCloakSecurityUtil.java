package com.max.springbootkeycloakusermanagement.util;

import lombok.Data;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KeyCloakSecurityUtil {

    private Keycloak keycloak;
    @Value("${config.keycloak.server-url}")
    private String serverUrl;
    @Value("${config.keycloak.realm}")
    private String realm;
    @Value("${config.keycloak.client-id}")
    private String clientId;
    @Value("${config.keycloak.client-secret}")
    private String clientSecret;
    @Value("config.keycloak.grant-type")
    private String grantType;

    public Keycloak getKeyCloakInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();
        }
        return keycloak;
    }

}
