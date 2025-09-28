package com.example;

import com.example.web.WebServer;

public class WebMain {
    public static void main(String[] args) {
        try {
            WebServer server = new WebServer();
            server.start();
        } catch (Exception e) {
            System.err.println("Failed to start web server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
