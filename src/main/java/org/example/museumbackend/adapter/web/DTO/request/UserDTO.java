package org.example.museumbackend.adapter.web.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserDTO(
        @Email
        String email,
        @NotNull
        @Length(min = 8)
        String password
) {
}
