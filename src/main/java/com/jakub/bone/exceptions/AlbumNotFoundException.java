package com.jakub.bone.exceptions;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException(String album) {
        super("Album not found: " + album);
    }
}
