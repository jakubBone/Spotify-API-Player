package com.jakub.bone.core;

import com.jakub.bone.service.AuthService;
import okhttp3.OkHttpClient;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class SpotifyPlayer {
    public static void main(String[] args) throws IOException, URISyntaxException {
        OkHttpClient client = new OkHttpClient();
        AuthService service = new AuthService(client);

        String authorizationUrl = service.getAuthorizationURL();
        System.out.println(authorizationUrl);

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(authorizationUrl));
        } else {
            System.out.println("Open the following URL in your browser: " + authorizationUrl);
        }

        try {
            Thread.sleep(15000);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }


    }
}

