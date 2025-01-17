package com.jakub.bone;

import com.jakub.bone.utills.Config;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;

public class AuthService {
    OkHttpClient client = new OkHttpClient();
    public void post() throws IOException {
        String credentials = Config.CLIENT_ID + ":" + Config.CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        RequestBody body = new FormBody.Builder()
                .add("grant_type","client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(Config.TOKEN_URL)
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("\n------------------------------------");
            System.out.println(responseBody);
            System.out.println("------------------------------------");        }
    }
}
