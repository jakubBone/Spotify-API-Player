package com.jakub.bone.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jakub.bone.utills.Config;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.jakub.bone.utills.Config.*;

public class AuthService {
    OkHttpClient client;
    public AuthService(OkHttpClient client) {
        this.client = client;
    }

    public String getAuthorizationURL(){
        return AUTH_URL +
                "?response_type=code" +
                "&client_id=" + CLIENT_ID +
                "&scope=" + URLEncoder.encode(SCOPES, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);
    }

    public String getAccessToken(String authCode) throws IOException {
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", authCode)
                .add("redirect_uri", REDIRECT_URI)
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .build();

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .post(body)
                .build();

        return parseAccessTokenFromResponse(request);
    }

    public String parseAccessTokenFromResponse(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                return jsonObject.get("access_token").getAsString();
            }
            throw new RuntimeException("Unsuccessful response: " + response.message());
        }
    }
}
