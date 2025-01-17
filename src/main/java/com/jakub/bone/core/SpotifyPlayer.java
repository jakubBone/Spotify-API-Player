package com.jakub.bone.core;

import com.jakub.bone.AuthService;

import java.io.IOException;

public class SpotifyPlayer {
    public static void main(String[] args) throws IOException {
        AuthService service = new AuthService();
        service.post();
    }
}
