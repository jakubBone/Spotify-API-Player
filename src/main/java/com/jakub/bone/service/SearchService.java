package com.jakub.bone.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jakub.bone.exceptions.AlbumNotFoundException;
import com.jakub.bone.exceptions.SpotifyAPIException;
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
        // Construct the search URL with query parameters:
        // q: the album name, URL-encoded to handle special characters
        // type: specifies that we're searching for an album
        // limit: restricts the results to the top 1 match
        String url = SEARCH_URL + "?q=" + URLEncoder.encode(album, StandardCharsets.UTF_8) + "&type=album&limit=1";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
               return parseAlbumId(response.body().string());
            } else if(response.code() == 404) {
                throw new AlbumNotFoundException("Album " + album + "not found");
            } else {
                throw new SpotifyAPIException("Unsuccessful response: " + response.message());
            }
        } catch (IOException ex) {
            throw new SpotifyAPIException("Error during searching album: " + album, ex);
        }
    }

    private String parseAlbumId(String responseBody) {
        // Structure: { "albums": { "items": [ { "id": "album_id", ... }, ... ] }, ... }
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonObject.getAsJsonObject("albums")
                .getAsJsonArray("items")
                .get(0)
                .getAsJsonObject()
                .get("id").getAsString();
    }
}
