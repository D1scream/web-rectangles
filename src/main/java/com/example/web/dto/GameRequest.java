package com.example.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameRequest {
    @JsonProperty("size")
    private int size;
    
    @JsonProperty("data")
    private String data;
    
    @JsonProperty("nextPlayerColor")
    private String nextPlayerColor;
    
    public GameRequest() {}
    
    public GameRequest(int size, String data, String nextPlayerColor) {
        this.size = size;
        this.data = data;
        this.nextPlayerColor = nextPlayerColor;
    }
    
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    
    public String getNextPlayerColor() { return nextPlayerColor; }
    public void setNextPlayerColor(String nextPlayerColor) { this.nextPlayerColor = nextPlayerColor; }
}