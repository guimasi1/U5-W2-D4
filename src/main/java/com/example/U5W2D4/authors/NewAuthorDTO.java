package com.example.U5W2D4.authors;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewAuthorDTO(
        @NotEmpty(message = "Inserire il nome")
        @Size(min = 3,max = 20, message = "Il nome deve essere di minimo 3 caratteri e massimo 20")
        String name,
        @NotEmpty(message = "Inserire il cognome")
        @Size(min = 3,max = 20, message = "Il cognome deve essere di minimo 3 caratteri e massimo 20")
        String surname,
        @NotEmpty(message = "Inserire l'email")
        @Email(message = "Inserire un'email valida")
        String email,
        LocalDate birthday,
        @NotEmpty(message = "Inserire l'url dell'avatar")
        String avatarUrl) {
}
