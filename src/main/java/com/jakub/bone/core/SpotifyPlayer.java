package com.jakub.bone.core;

import com.jakub.bone.server.CallbackServer;
import com.jakub.bone.service.AuthService;
import com.jakub.bone.service.SearchService;
import okhttp3.OkHttpClient;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;


public class SpotifyPlayer {
    private CallbackServer server;
    private OkHttpClient client;
    private AuthService authService;
    private SearchService searchService;

    public SpotifyPlayer() throws IOException {
        this.server = new CallbackServer();
        this.server.startServer();
        this.client = new OkHttpClient();
        this.authService = new AuthService(client);
        this.searchService = new SearchService(client);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            new SpotifyPlayer().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException, InterruptedException, URISyntaxException {
        String albumName = promptAlbumName();

        System.out.println("Login on Spotify website...");
        openUrl(authService.getAuthorizationURL());

        String authCode = waitForAuthCode();

        String token = authService.getAccessToken(authCode);

        String albumId = searchService.searchAlbum(token, albumName);
        if (albumId == null) {
            System.err.println("Album not found");
        } else {
            openUrl("https://open.spotify.com/album/" + albumId);
            System.out.println("Opening album in Spotify Web Player...");
        }
        server.stopServer();
    }

    private String promptAlbumName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello. Please, type album name:");
        return scanner.nextLine();
    }

    private String waitForAuthCode() throws InterruptedException {
        while (server.getAuthCode() == null) {
            System.out.println("Waiting for user to log in via Spotify...");
            Thread.sleep(5000);
        }
        return server.getAuthCode();
    }

    private void openUrl(String url) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            System.err.println("Desktop not supported!");
        }
    }
}

