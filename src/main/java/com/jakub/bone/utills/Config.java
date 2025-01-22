package com.jakub.bone.utills;

public class Config {
    // CLIENT CREDENTIALS
    public static final String CLIENT_ID = ConfigLoader.get("client.ID");
    public static final String CLIENT_SECRET = ConfigLoader.get("client.secret");

    // URL
    public static final String AUTH_URL = ConfigLoader.get("url.auth");
    public static final String TOKEN_URL = ConfigLoader.get("url.token");
    public static final String SEARCH_URL = ConfigLoader.get("url.search");
    public static final String ALBUM_URL = ConfigLoader.get("url.album");

    // URI
    public static final String REDIRECT_URI = ConfigLoader.get("uri.redirect");

    // BROWSER RESPONSES
    public static final String RESPONSE_SUCCEED = ConfigLoader.get("response.succeed");
    public static final String RESPONSE_FAILED = ConfigLoader.get("response.failed");

    // ACCESS SCOPES
    public static final String SCOPES = ConfigLoader.get("access.scopes");
}
