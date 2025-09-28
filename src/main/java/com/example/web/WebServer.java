package com.example.web;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.example.web.handler.MoveHandler;
import com.example.web.service.GameService;
import com.sun.net.httpserver.HttpServer;

public class WebServer {
    
    private static final int PORT = 8080;
    private static final String MOVE_ENDPOINT = "/api/move";
    
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        GameService gameService = new GameService();
        
        server.createContext(MOVE_ENDPOINT, new MoveHandler(gameService));
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("Web server started on port " + PORT);
    }
}
