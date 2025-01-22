package com.jakub.bone.server;

import com.jakub.bone.utills.Config;
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

        String response;
        int statusCode;

        if (query != null && query.contains("code=")) {
            String code = extractParamValue(query, "code");
            if (code != null) {
                this.authCode = code;
                response = Config.RESPONSE_SUCCEED;
                statusCode = 200;
            } else {
                response = Config.RESPONSE_FAILED;
                statusCode = 400;
            }
            exchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
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

    public String getAuthCode() {
        return authCode;
    }

    public void startServer(){
        server.start();
    }

    public void stopServer(){
        server.stop(0);
    }
}

