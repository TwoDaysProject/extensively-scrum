package com.extensivelyscrum.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateUserDto {
    private String fullName;
    private String email;
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
