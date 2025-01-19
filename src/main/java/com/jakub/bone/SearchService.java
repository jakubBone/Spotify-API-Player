package com.jakub.bone;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class SearchService {
    OkHttpClient client;
    public SearchService(OkHttpClient client) {
        this.client = client;
    }

    public String searchAlbum(String accessToken, String album) {
        String url = "https://api.spotify.com/v1/search?q=" + URLEncoder.encode(album, StandardCharsets.UTF_8) + "&type=album&limit=1";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                return jsonObject.toString();
            }
            System.err.println("Error: " + response.code() + " - " + response.message());
            System.err.println("Response body: " + response.body().string());
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
