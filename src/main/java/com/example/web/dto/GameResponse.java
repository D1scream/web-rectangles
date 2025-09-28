package com.example.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameResponse {
    @JsonProperty("move")
    private Move move;
    
    public GameResponse() {}
    
    public GameResponse(Move move) {
        this.move = move;
    }
    
    public Move getMove() { return move; }
    public void setMove(Move move) { this.move = move; }
    
    public static class Move {
        @JsonProperty("x")
        private int x;
        
        @JsonProperty("y")
        private int y;
        
        @JsonProperty("color")
        private String color;
        
        public Move() {}
        
        public Move(int x, int y, String color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
        
        public int getX() { return x; }
        public void setX(int x) { this.x = x; }
        
        public int getY() { return y; }
        public void setY(int y) { this.y = y; }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }
}
