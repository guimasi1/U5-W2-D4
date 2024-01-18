package com.example.U5W2D4.blogs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

public record NewBlogDTO (
        @NotEmpty(message = "Inserire la categoria")
        @Size(min = 3, max = 20, message = "La categoria deve avere minimo 3 caratteri e massimo 20 caratteri")
        String category,
        @NotEmpty(message = "Inserire il titolo")
        @Size(min = 3, max = 20, message = "Il titolo deve avere minimo 3 caratteri e massimo 20 caratteri")
        String title,
        @NotEmpty(message = "Inserire l'url della cover")
        String coverUrl,
        @NotEmpty(message = "Inserire il contenuto")
        @Size(min = 3, max = 300, message = "Il contenuto deve avere minimo 3 caratteri e massimo 300 caratteri")
        String content,
        @NotEmpty(message = "Inserire il tempo di lettura")
        int readingTime,
        @NotEmpty(message = "Inserire l'url dell'autore")
        UUID authorURL) {

}
