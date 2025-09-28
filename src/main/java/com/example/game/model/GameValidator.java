package com.example.game.model;

public class GameValidator {
    
    public static void validateBoardSize(int size) {
        if (size < GameConstants.MIN_BOARD_SIZE) {
            throw new IllegalArgumentException("Board size must be >= " + GameConstants.MIN_BOARD_SIZE);
        }
        if (size > GameConstants.MAX_BOARD_SIZE) {
            throw new IllegalArgumentException("Board size must be <= " + GameConstants.MAX_BOARD_SIZE);
        }
    }
    
    public static void validateMove(int x, int y, int boardSize) {
        if (x < 0 || x >= boardSize) {
            throw new IllegalArgumentException(String.format("Invalid move: x=%d is out of bounds. Board size is %dx%d", x, boardSize, boardSize));
        }
        
        if (y < 0 || y >= boardSize) {
            throw new IllegalArgumentException(String.format("Invalid move: y=%d is out of bounds. Board size is %dx%d", y, boardSize, boardSize));
        }
    }
    
    public static void validateColor(char color) {
        if (color != GameConstants.WHITE_PIECE && color != GameConstants.BLACK_PIECE) {
            throw new IllegalArgumentException(String.format("Invalid color: '%c'. Only '%c' (white) and '%c' (black) are allowed", 
                color, GameConstants.WHITE_PIECE, GameConstants.BLACK_PIECE));
        }
    }
    
    public static void validateCellNotOccupied(char cellValue, int x, int y) {
        if (cellValue != GameConstants.EMPTY_CELL) {
            throw new IllegalArgumentException(String.format("Invalid move: cell (%d, %d) is already occupied by '%c'", x, y, cellValue));
        }
    }
}
