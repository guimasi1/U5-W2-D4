package com.example.U5W2D4.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsPayload {
    private String message;
    private LocalDateTime dateTime;
}
