package com.example.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;


public class Board {
    private final int size;
    private char[][] grid;
    private Pair<Integer, Integer> lastMove;
    public Board(int size) {
        if(size < 3) {
            throw new IllegalArgumentException("Board size must be > 2");
        }
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
        if (x < 0 || x >= size) {
            throw new IllegalArgumentException(String.format("Invalid move: x=%d is out of bounds. Board size is %dx%d", x, size, size));
        }
        
        if (y < 0 || y >= size) {
            throw new IllegalArgumentException(String.format("Invalid move: y=%d is out of bounds. Board size is %dx%d", y, size, size));
        }
        
        if (grid[x][y] != '.') {
            throw new IllegalArgumentException(String.format("Invalid move: cell (%d, %d) is already occupied by '%c'", x, y, grid[x][y]));
        }
        
        if (color != 'w' && color != 'b') {
            throw new IllegalArgumentException(String.format("Invalid color: '%c'. Only 'w' (white) and 'b' (black) are allowed", color));
        }
        grid[x][y] = color;
        lastMove = Pair.of(x, y);
    }
    

    public char[][] getGrid() {
        return grid;
    }
    
    public int getSize() {
        return size;
    }

    public Pair<Integer, Integer> getLastMove() {
        return lastMove;
    }
    
    public void setGrid(char[][] grid) {
        this.grid = grid;
    }
}