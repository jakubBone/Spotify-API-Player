package com.jakub.bone;

import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SpotifySearchService {
    OkHttpClient client = new OkHttpClient();
    public String searchAlbum(String accessToken, String albumName)  {
        String url = "https://api.spotify.com/v1/search?q=" + URLEncoder.encode(albumName, StandardCharsets.UTF_8) +"&type=album&limit=1";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
