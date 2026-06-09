package com.timetrack.auth.Dto;

import lombok.Data;

@Data
public class LoginJWTDTO {
    private String username;
    private String password;
}
