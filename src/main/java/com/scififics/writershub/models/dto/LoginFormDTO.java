package com.scififics.writershub.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormDTO {

    @NotNull (message = " - Username is required")
    @NotBlank (message = " - Username cannot be blank")
    @Size (max = 20, message = " - Username cannot be over 20 characters long")
    private String username;

    @NotNull (message = " - Password is required")
    @NotBlank (message = " - Password cannot be blank")
    @Size (min = 6, max = 20, message = " - Password must be between 6 and 20 characters long")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
