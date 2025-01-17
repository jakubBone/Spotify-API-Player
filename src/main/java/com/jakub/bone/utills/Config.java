package com.jakub.bone.utills;

public class Config {
    // Client
    public static final String CLIENT_ID = ConfigLoader.get("client.ID");
    public static final String CLIENT_SECRET = ConfigLoader.get("client.secret");

    // URL
    public static final String AUTH_URL = ConfigLoader.get("url.auth");
    public static final String REDIRECT_URL = ConfigLoader.get("url.redirect");
    public static final String TOKEN_URL = ConfigLoader.get("url.token");

}
