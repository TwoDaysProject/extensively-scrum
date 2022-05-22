package com.extensivelyscrum.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class CreateUserDto {
    @NotNull(message = "fullName should not be null!")
    @NotEmpty(message = "fullName must not be an empty string")
    private String fullName;
    @NotNull(message = "Email should not be null!")
    @NotEmpty(message = "Email must not be an empty string")
    @Email(message = "Not an email")
    private String email;
    @NotNull(message = "password should not be null!")
    @NotEmpty(message = "password must not be an empty string")
    private String password;

    public CreateUserDto(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
