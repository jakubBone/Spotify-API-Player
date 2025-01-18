package com.jakub.bone;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jakub.bone.utills.Config;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;

public class AuthService {
    OkHttpClient client = new OkHttpClient();

    public String getAccessToken() throws IOException {
        Request request = buildAccessTokenRequest();
        return getResponse(request);
    }

    public Request buildAccessTokenRequest() throws IOException {
        String credentials = Config.CLIENT_ID + ":" + Config.CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        return new Request.Builder()
                .url(Config.TOKEN_URL)
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .post(body)
                .build();
    }

    public String getResponse(Request request) throws IOException {
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
