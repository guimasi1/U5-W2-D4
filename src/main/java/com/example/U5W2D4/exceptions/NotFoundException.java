package com.example.U5W2D4.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{
    public NotFoundException(UUID id) {
        super("item with " + id + " not found.");
    }
}
