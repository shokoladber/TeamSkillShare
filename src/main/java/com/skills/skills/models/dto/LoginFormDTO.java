package com.skills.skills.models.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormDTO {

    @NotNull
    @NotBlank
    @Size(max= 20, message ="Username must be less than 20 characters.")
    private String username;

    @NotNull
    @NotBlank
    @Size(min= 6, max= 50, message = "Password must be between 6 and 50 characters.")
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


