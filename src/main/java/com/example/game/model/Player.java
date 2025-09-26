package com.example.game.model;

public class Player {
    private final String type;
    private final char color;
    
    public Player(String type, char color) {
        this.type = type;
        this.color = color;
    }
    
    @Override
    public String toString() {
        return type + " (" + color + ")";
    }

    public boolean isComputer() {
        return type.equals("comp");
    }

    public char getColor() {
        return color;
    }
}
