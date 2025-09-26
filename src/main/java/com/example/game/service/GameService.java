package com.example.game.service;

import com.example.game.model.Game;

public class GameService {
    
    public Game createGame(int boardSize, String player1Type, char player1Color, 
                          String player2Type, char player2Color) {
        validateGameParameters(boardSize, player1Type, player1Color, player2Type, player2Color);
        return new Game(boardSize, player1Type, player1Color, player2Type, player2Color);
    }
    
    private void validateGameParameters(int boardSize, String player1Type, char player1Color, 
                                      String player2Type, char player2Color) {
        if (boardSize <= 2) {
            throw new IllegalArgumentException("Board size must be > 2");
        }
        
        if (!isValidPlayerType(player1Type) || !isValidPlayerType(player2Type)) {
            throw new IllegalArgumentException("Player type must be 'user' or 'comp'");
        }
        
        if (!isValidColor(player1Color) || !isValidColor(player2Color)) {
            throw new IllegalArgumentException("Color must be 'w' or 'b'");
        }
        
        if (player1Color == player2Color) {
            throw new IllegalArgumentException("Players must have different colors");
        }
    }
    
    private boolean isValidPlayerType(String type) {
        return "user".equals(type) || "comp".equals(type);
    }
    
    private boolean isValidColor(char color) {
        return color == 'w' || color == 'b';
    }
}
