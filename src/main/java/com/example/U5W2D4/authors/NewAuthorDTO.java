package com.example.U5W2D4.authors;


import java.time.LocalDate;

public record NewAuthorDTO(String name, String surname, String email, LocalDate birthday, String avatarUrl) {
}
