package com.max.springbootkeycloakusermanagement.rest;

import com.max.springbootkeycloakusermanagement.dto.User;
import com.max.springbootkeycloakusermanagement.util.KeyCloakSecurityUtil;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserManagementController {

    @Autowired
    private KeyCloakSecurityUtil keyCloakSecurityUtil;

    @Value("${config.keycloak.realm}")
    private String realm;

    @GetMapping("/users")
    public List<User> getUsers() {
        Keycloak keycloak = keyCloakSecurityUtil.getKeyCloakInstance();
        List<UserRepresentation> userRepresentationList = keycloak.realm(realm).users().list();
        List<User> users = mapUsers(userRepresentationList);
        System.out.println(keycloak.realm(realm).users().list());
        System.out.println(users);
        return users;
    }

    @PostMapping("/user")
    public String createUser(@RequestBody User user) {
        Keycloak keycloak = keyCloakSecurityUtil.getKeyCloakInstance();
        UserRepresentation userRepresentation = mapUserToUserRepresentation(user);
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        System.out.printf("response :: "+response.toString());
        /*List<User> users = mapUsers(userRepresentationList);
        System.out.println(keycloak.realm(realm).users().list());
        System.out.println(users);
        return users;*/
        return "Success";
    }

    public List<User> mapUsers(List<UserRepresentation> userRepresentationList) {
        List<User> users = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userRepresentationList)) {
            userRepresentationList.stream().forEach(userRepresentation -> {
                        User user = new User();
                        user.setFirstname(userRepresentation.getFirstName());
                        user.setLastName(userRepresentation.getLastName());
                        user.setUserName(userRepresentation.getUsername());
                        user.setEmail(userRepresentation.getEmail());
                        users.add(user);
                    }
            );
        }
        return users;
    }

    public UserRepresentation mapUserToUserRepresentation(User user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getUserName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setUsername(user.getUserName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        List<CredentialRepresentation> credentialRepresentations = new ArrayList<>();
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentations.add(credentialRepresentation);
        userRepresentation.setCredentials(credentialRepresentations);
        return userRepresentation;
    }
}
