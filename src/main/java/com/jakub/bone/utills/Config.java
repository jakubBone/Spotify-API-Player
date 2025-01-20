package com.jakub.bone.utills;

public class Config {
    // CREDENTIALS
    public static final String CLIENT_ID = ConfigLoader.get("client.ID");
    public static final String CLIENT_SECRET = ConfigLoader.get("client.secret");

    // URL
    public static final String AUTH_URL = ConfigLoader.get("url.auth");
    public static final String REDIRECT_URI = ConfigLoader.get("uri.redirect");
    public static final String TOKEN_URL = ConfigLoader.get("url.token");
    public static final String SEARCH_URL = ConfigLoader.get("url.search");
    private static final String PLAYER_URL = ConfigLoader.get("url.player");

}
