package com.jakub.bone.utills;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class PlayerService {
    OkHttpClient client;
    public PlayerService(OkHttpClient client) {
        this.client = client;
    }

    public void playMusic(String accessToken, String albumName){
        String url = "https://api.spotify.com/v1/me/player/play";

        String uri = getAlbumUri(albumName);

        RequestBody body = new FormBody.Builder()
                .add("context_uri", uri)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Playing...");
            } else {
                System.err.println("Error during playing: " + response.code() + " - " + response.message());
                System.err.println("Response body: " + response.body().string());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error during album playing", ex);
        }
    }

    public String getAlbumUri(String album){
        JsonObject jsonObject = JsonParser.parseString(album).getAsJsonObject();
        return jsonObject.getAsJsonObject("albums")
                .getAsJsonArray("items")
                .get(0)
                .getAsJsonObject()
                .get("uri").getAsString();

    }
}
