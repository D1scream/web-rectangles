package com.example.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;



public class Board {
    private char[][] grid;
    
    public static final String RUNNING = "running";
    public static final String DRAW = "draw";
    public static final String WHITE_WIN = "white_win";
    public static final String BLACK_WIN = "black_win";
    
    private String gameStatus = RUNNING;
    private char winner;
    public Board(int size) {
        if(size < 3) {
            throw new IllegalArgumentException("Board size must be > 2");
        }
        this.grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public List<Pair<Integer, Integer>> getEmptyCells() {
        List<Pair<Integer, Integer>> emptyCells = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (grid[i][j] == '.') {
                    emptyCells.add(Pair.of(i, j));
                }
            }
        }
        return emptyCells;
    }

    public void makeMove(int x, int y, char color) {
        if (!gameStatus.equals(RUNNING)) {
            throw new IllegalStateException("Game is over");
        }
        
        if (x < 0 || x >= getSize()) {
            throw new IllegalArgumentException(String.format("Invalid move: x=%d is out of bounds. Board size is %dx%d", x, getSize(), getSize()));
        }
        
        if (y < 0 || y >= getSize()) {
            throw new IllegalArgumentException(String.format("Invalid move: y=%d is out of bounds. Board size is %dx%d", y, getSize(), getSize()));
        }
        
        if (grid[x][y] != '.') {
            throw new IllegalArgumentException(String.format("Invalid move: cell (%d, %d) is already occupied by '%c'", x, y, grid[x][y]));
        }
        
        if (color != 'w' && color != 'b') {
            throw new IllegalArgumentException(String.format("Invalid color: '%c'. Only 'w' (white) and 'b' (black) are allowed", color));
        }
        grid[x][y] = color;
        checkGameStatus();
    }

    public Character checkWin() {
        int n = getSize();
        char[][] g = getGrid();
    
        List<int[]> pts = new ArrayList<>();
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (g[x][y] != '.' && g[x][y] != 0) {
                    pts.add(new int[]{x, y});
                }
            }
        }
    
        int m = pts.size();
        for (int i = 0; i < m; i++) {
            int[] a = pts.get(i);
            char c = g[a[0]][a[1]];
    
            for (int j = i + 1; j < m; j++) {
                int[] b = pts.get(j);
                if (g[b[0]][b[1]] != c) continue;
    
                int vx = b[0] - a[0];
                int vy = b[1] - a[1];
                int wx = -vy, wy = vx;
    
                int c1x = a[0] + wx, c1y = a[1] + wy;
                int d1x = b[0] + wx, d1y = b[1] + wy;
                if (inside(c1x, c1y, n) && inside(d1x, d1y, n)) {
                    if (g[c1x][c1y] == c && g[d1x][d1y] == c) {
                        return c;
                    }
                }
    
                int c2x = a[0] - wx, c2y = a[1] - wy;
                int d2x = b[0] - wx, d2y = b[1] - wy;
                if (inside(c2x, c2y, n) && inside(d2x, d2y, n)) {
                    if (g[c2x][c2y] == c && g[d2x][d2y] == c) {
                        return c;
                    }
                }
            }
        }
        return null;
    }
    
    
    
    
    private boolean inside(int x, int y, int n) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
    
    public boolean checkDraw() {
        return getEmptyCells().isEmpty();
    }
    
    public void checkGameStatus() {
        Character winColor = checkWin();
        if (winColor != null) {
            if (winColor == 'w') {
                gameStatus = WHITE_WIN;
            } else {
                gameStatus = BLACK_WIN;
            }
            winner = winColor;
        } else if (checkDraw()) {
            gameStatus = DRAW;
        } else {
            gameStatus = RUNNING;
        }
    }
    
    
    public String getGameStatus() {
        return gameStatus;
    }
    
    public char getWinner() {
        return winner;
    }

    public char[][] getGrid() {
        return grid;
    }
    
    public void setGrid(char[][] newGrid) {
        this.grid = newGrid;

        checkGameStatus();
    }
    
    public int getSize() {
        return grid.length;
    }
    
    
    public int getWhiteCount() {
        int count = 0;
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (grid[i][j] == 'w') {
                    count++;
                }
            }
        }
        return count;
    }
    
    public int getBlackCount() {
        int count = 0;
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (grid[i][j] == 'b') {
                    count++;
                }
            }
        }
        return count;
    }
    
    public char getCurrentPlayerColor() {  
        if (getWhiteCount() > getBlackCount()) {
            return 'b';
        }
        
        return 'w';
    }
    
}