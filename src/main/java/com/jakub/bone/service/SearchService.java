package com.jakub.bone.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.jakub.bone.utills.Config.SEARCH_URL;


public class SearchService {
    OkHttpClient client;
    public SearchService(OkHttpClient client) {
        this.client = client;
    }

    public String searchAlbum(String accessToken, String album) {
        String url = SEARCH_URL + "?q=" + URLEncoder.encode(album, StandardCharsets.UTF_8) + "&type=album&limit=1";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
               return parseAlbumId(response.body().string());
            } else {
                throw new RuntimeException("Unsuccessful response: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseAlbumId(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonObject.getAsJsonObject("albums")
                .getAsJsonArray("items")
                .get(0)
                .getAsJsonObject()
                .get("id").getAsString();
    }
}
