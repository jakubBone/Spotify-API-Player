package com.jakub.bone.core;

import com.jakub.bone.exceptions.AlbumNotFoundException;
import com.jakub.bone.exceptions.SpotifyAPIException;
import com.jakub.bone.server.CallbackServer;
import com.jakub.bone.service.AuthService;
import com.jakub.bone.service.SearchService;
import okhttp3.OkHttpClient;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import static com.jakub.bone.utills.Config.ALBUM_URL;

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

    public static void main(String[] args) throws IOException, SpotifyAPIException {
        try {
            new SpotifyPlayer().run();
        } catch (SpotifyAPIException ex) {
            System.err.println("Application error: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("Invalid URI encountered: " + ex.getMessage());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.err.println("Application execution was interrupted: " + ex.getMessage());
        } catch (IOException ex){
            System.err.println("I/O error occurred: " + ex.getMessage());
        } finally {
            System.out.println("Application stopped");
        }
    }

    private void run() throws IOException, URISyntaxException, InterruptedException {
        String album = promptAlbumName();

        System.out.println("Login on Spotify website...");
        openUrl(authService.getAuthorizationURL());

        String authCode = waitForAuthCode();

        String token = authService.getAccessToken(authCode);

        String albumId = searchService.searchAlbum(token, album);
        if (albumId == null) {
            throw new AlbumNotFoundException("Album " + album + "not found");

        } else {
            openUrl(ALBUM_URL + albumId);
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
            Thread.sleep(1000);
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

