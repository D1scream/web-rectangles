package com.example.game.model;

public final class GameConstants {
    
    public static final char WHITE_PIECE = 'w';
    public static final char BLACK_PIECE = 'b';
    public static final char EMPTY_CELL = '.';
    
    public static final String RUNNING = "running";
    public static final String DRAW = "draw";
    public static final String WHITE_WIN = "white_win";
    public static final String BLACK_WIN = "black_win";
    
    public static final int MIN_BOARD_SIZE = 3;
    public static final int MAX_BOARD_SIZE = 100;
    
    public static final String USER_PLAYER_TYPE = "user";
    public static final String COMPUTER_PLAYER_TYPE = "comp";
    
    private GameConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
}
