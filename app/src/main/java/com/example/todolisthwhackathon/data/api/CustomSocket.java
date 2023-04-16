package com.example.todolisthwhackathon.data.api;

import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;

import dev.gustavoavila.websocketclient.WebSocketClient;

public class CustomSocket {
    private WebSocketClient webSocketClient;

    public void createWebSocketClient() {
        URI uri;
        try {
            uri = new URI("http://127.0.0.1:61613");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("res", "onOpen");
                webSocketClient.send("Hello, World!");
            }

            @Override
            public void onTextReceived(String message) {
                Log.i("res", "onTextReceived");
            }

            @Override
            public void onBinaryReceived(byte[] data) {
                Log.i("res", "onBinaryReceived");
            }

            @Override
            public void onPingReceived(byte[] data) {
                Log.i("res", "onPingReceived");
            }

            @Override
            public void onPongReceived(byte[] data) {
                Log.i("res", "onPongReceived");
            }

            @Override
            public void onException(Exception e) {
                Log.i("res", e.getMessage());
            }

            @Override
            public void onCloseReceived(int reason, String description) {
                Log.i("res", "onCloseReceived");
            }
        };

        webSocketClient.setConnectTimeout(1000);
        webSocketClient.setReadTimeout(6000);
//            webSocketClient.addHeader("Origin", "http://developer.example.com");
        webSocketClient.enableAutomaticReconnection(500);
        webSocketClient.connect();
    }
}