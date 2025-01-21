package com.jakub.bone.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class CallbackServer {
    private HttpContext context;
    private HttpServer server;
    private String authCode;

    public CallbackServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8080), 0);
        this.context = server.createContext("/", this::handleCallback);
    }

    private void handleCallback(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();

        if (query != null && query.contains("code=")) {
            String code = extractParamValue(query, "code");
            if (code != null) {
                this.authCode = code;
                System.out.println("Code: " + code);
            }
        }
    }

    private String extractParamValue(String query, String paramName) {
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2 && kv[0].equals(paramName)) {
                return kv[1];
            }
        }
        return null;
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    public String getAuthCode() {
        return authCode;
    }
}

