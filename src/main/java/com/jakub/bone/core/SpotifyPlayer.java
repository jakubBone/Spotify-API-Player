package com.jakub.bone.core;

import com.jakub.bone.AuthService;
import com.jakub.bone.SearchService;

import java.io.IOException;

public class SpotifyPlayer {
    public static void main(String[] args) throws IOException {
        AuthService service = new AuthService();
        String accessToken = service.getAccessToken().trim();

        SearchService searchService = new SearchService();
        String album = searchService.searchAlbum(accessToken, "Męskie Granie");
        System.out.println(album);
    }
}
