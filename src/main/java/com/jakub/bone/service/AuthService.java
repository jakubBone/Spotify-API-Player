package com.jakub.bone.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jakub.bone.utills.Config;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthService {
    OkHttpClient client;
    public AuthService(OkHttpClient client) {
        this.client = client;
    }

    public String getAuthorizationURL(){
        String scopes = "playlist-read-private user-modify-playback-state";

        return "https://accounts.spotify.com/authorize" +
                "?response_type=code" +
                "&client_id=" + Config.CLIENT_ID +
                "&scope=" + URLEncoder.encode(scopes, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(Config.REDIRECT_URI, StandardCharsets.UTF_8);
    }

    public String getAccessToken(String authCode) throws IOException {
        String credentials = Config.CLIENT_ID + ":" + Config.CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", authCode)
                .add("redirect_url", Config.REDIRECT_URI)
                .add("client_id", Config.CLIENT_ID)
                .add("client_secret", Config.CLIENT_SECRET)
                .build();

        Request request = new Request.Builder()
                .url(Config.TOKEN_URL)
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .post(body)
                .build();

        return getTokenResponse(request);
    }

    public String getTokenResponse(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                return jsonObject.get("access_token").getAsString();
            }
            System.err.println("Error: " + response.code() + " - " + response.message());
            System.err.println("Response body: " + response.body().string());
        }
        return null;
    }
}
