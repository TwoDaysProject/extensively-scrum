package com.extensivelyscrum.backend.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateUserDto (
    @NotNull(message = "fullName should not be null!")
    @NotEmpty(message = "fullName must not be an empty string")
    String fullName,

    @NotNull(message = "Email should not be null!")
    @NotEmpty(message = "Email must not be an empty string")
    @Email(message = "Not an email")
    String email,

    @NotNull(message = "password should not be null!")
    @NotEmpty(message = "password must not be an empty string")
    String password
    ) {
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
