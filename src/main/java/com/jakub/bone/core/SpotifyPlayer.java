package com.jakub.bone.core;

import com.jakub.bone.server.CallbackServer;
import com.jakub.bone.service.AuthService;
import com.jakub.bone.service.SearchService;
import okhttp3.OkHttpClient;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class SpotifyPlayer {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        CallbackServer server = new CallbackServer();
        server.start();

        OkHttpClient client = new OkHttpClient();
        AuthService authService = new AuthService(client);
        SearchService searchService = new SearchService(client);


        String authorizationUrl = authService.getAuthorizationURL();
        System.out.println(authorizationUrl);

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(authorizationUrl));
        }

        while (server.getAuthCode() == null) {
            System.out.println("Waiting for user to log in via Spotify...");
            Thread.sleep(1000);
        }

        String token = authService.getAccessToken(server.getAuthCode());
        String album = searchService.searchAlbum(token, "Męskie Granie");

    }
}

