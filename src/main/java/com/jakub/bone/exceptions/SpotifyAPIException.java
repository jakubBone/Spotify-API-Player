package com.jakub.bone.exceptions;

public class SpotifyAPIException extends RuntimeException {
    public SpotifyAPIException(String message) {
        super(message);
    }

    public SpotifyAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
