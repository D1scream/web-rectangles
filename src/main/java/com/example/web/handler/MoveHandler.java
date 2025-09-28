package com.example.web.handler;

import java.io.IOException;

import com.example.web.dto.GameRequest;
import com.example.web.dto.GameResponse;
import com.example.web.exception.GameDataException;
import com.example.web.exception.GameValidationException;
import com.example.web.service.GameService;
import com.example.web.util.HttpUtils;
import com.example.web.util.JsonUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MoveHandler implements HttpHandler {
    private final GameService gameService;
    
    public MoveHandler() {
        this.gameService = new GameService();
    }
    
    public MoveHandler(GameService gameService) {
        this.gameService = gameService;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HttpUtils.addCORSHeaders(exchange);
        
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            HttpUtils.sendResponse(exchange, 200, "");
            return;
        }
        
        if (!"POST".equals(exchange.getRequestMethod())) {
            HttpUtils.sendError(exchange, 405, "Method Not Allowed");
            return;
        }
        
        try {
            String requestBody = HttpUtils.readRequestBody(exchange);
            GameRequest request = JsonUtils.fromJson(requestBody, GameRequest.class);
            
            GameResponse response = gameService.calculateMove(request);
            String jsonResponse = JsonUtils.toJson(response);
            
            HttpUtils.sendResponse(exchange, 200, jsonResponse);
            
        } catch (GameDataException e) {
            HttpUtils.sendError(exchange, 422, "Invalid game data: " + e.getMessage());
        } catch (GameValidationException e) {
            HttpUtils.sendError(exchange, 400, "Validation error: " + e.getMessage());
        } catch (IOException e) {
            HttpUtils.sendError(exchange, 400, "Bad request: " + e.getMessage());
        } catch (Exception e) {
            HttpUtils.sendError(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
