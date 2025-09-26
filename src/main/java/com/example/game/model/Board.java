package com.example.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;


public class Board {
    private final int size;
    private final char[][] grid;
    
    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public List<Pair<Integer, Integer>> getEmptyCells() {
        List<Pair<Integer, Integer>> emptyCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == '.') {
                    emptyCells.add(Pair.of(i, j));
                }
            }
        }
        return emptyCells;
    }

    public void makeMove(int x, int y, char color) {
        String errorMessage = validateMove(x, y, color);
        if (errorMessage != null) {
            throw new IllegalArgumentException(errorMessage);
        }
        
        grid[x][y] = color;
    }
    
    public String isValidMove(int x, int y, char color) {
        String errorMessage = validateMove(x, y, color);
        if (errorMessage != null) {
            return errorMessage;
        }
        return null;
    }
    
    private String validateMove(int x, int y, char color) {
        if (x < 0 || x >= size) {
            return String.format("Invalid move: x=%d is out of bounds. Board size is %dx%d", x, size, size);
        }
        
        if (y < 0 || y >= size) {
            return String.format("Invalid move: y=%d is out of bounds. Board size is %dx%d", y, size, size);
        }
        
        if (grid[x][y] != '.') {
            return String.format("Invalid move: cell (%d, %d) is already occupied by '%c'", x, y, grid[x][y]);
        }
        
        if (color != 'w' && color != 'b') {
            return String.format("Invalid color: '%c'. Only 'w' (white) and 'b' (black) are allowed", color);
        }
        
        return null;
    }

    public char[][] getGrid() {
        return grid;
    }
}