package com.example.game;

import lombok.Getter;

@Getter
public class Player {
    private final String type;
    private final String color;
    
    public Player(String type, String color) {
        this.type = type;
        this.color = color;
    }
    
    @Override
    public String toString() {
        return type + " (" + color + ")";
    }
}
