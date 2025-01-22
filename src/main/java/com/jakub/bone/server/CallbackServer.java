package com.jakub.bone.server;

import com.jakub.bone.exceptions.ParameterNotFoundException;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import static com.jakub.bone.utills.Config.RESPONSE_FAILED;
import static com.jakub.bone.utills.Config.RESPONSE_SUCCEED;

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
                response = RESPONSE_SUCCEED;
                statusCode = 200;
            } else {
                response = RESPONSE_FAILED;
                statusCode = 400;
            }
            exchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private String extractParamValue(String query, String param) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Query string is null or empty");
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2 && kv[0].equals(param)) {
                return kv[1];
            }
        }
        throw new ParameterNotFoundException("Parameter '" + param + "' not found in query: " + query);
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

