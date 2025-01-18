package com.jakub.bone.core;

import com.jakub.bone.AuthService;
import com.jakub.bone.SearchService;
import okhttp3.OkHttpClient;

import java.io.IOException;

public class SpotifyPlayer {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();

        AuthService service = new AuthService(client);
        String accessToken = service.getAccessToken().trim();

        SearchService searchService = new SearchService(client);
        String album = searchService.searchAlbum(accessToken, "Męskie Granie");
        System.out.println(album);
    }
}
