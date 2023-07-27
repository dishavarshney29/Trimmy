package com.github.dishavarshney.trimmy.exceptions;

public class InvalidCustomShortUrl extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidCustomShortUrl(final String message) {
        super(message);
    }
}
