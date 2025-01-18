package com.jakub.bone.core;

import com.jakub.bone.AuthService;
import com.jakub.bone.SpotifySearchService;

import java.io.IOException;

public class SpotifyPlayer {
    public static void main(String[] args) throws IOException {
        AuthService service = new AuthService();
        String accessToken = service.getAccessToken().trim();

        SpotifySearchService searchService = new SpotifySearchService();
        String album = searchService.searchAlbum(accessToken, "Małomiasteczkowy");
        System.out.println(album);
    }
}
