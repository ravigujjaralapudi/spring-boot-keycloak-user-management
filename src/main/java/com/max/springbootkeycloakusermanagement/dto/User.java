package com.max.springbootkeycloakusermanagement.dto;

import lombok.Data;

@Data
public class User {
    private String firstname;
    private String lastName;
    private String email;
    private String userName;
    private String password;
}
