package com.example.game.model;

import java.util.ArrayList;
import java.util.List;

public class WinChecker {
    
    public Character checkWin(char[][] grid, int size) {
        List<int[]> pts = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[x][y] != GameConstants.EMPTY_CELL && grid[x][y] != 0) {
                    pts.add(new int[]{x, y});
                }
            }
        }
    
        int m = pts.size();
        for (int i = 0; i < m; i++) {
            int[] a = pts.get(i);
            char c = grid[a[0]][a[1]];
    
            for (int j = i + 1; j < m; j++) {
                int[] b = pts.get(j);
                if (grid[b[0]][b[1]] != c) continue;
    
                if (isSquare(a, b, c, grid, size)) {
                    return c;
                }
            }
        }
        return null;
    }
    
    private boolean isSquare(int[] a, int[] b, char color, char[][] grid, int size) {
        int vx = b[0] - a[0];
        int vy = b[1] - a[1];
        int wx = -vy, wy = vx;
    
        int c1x = a[0] + wx, c1y = a[1] + wy;
        int d1x = b[0] + wx, d1y = b[1] + wy;
        if (inside(c1x, c1y, size) && inside(d1x, d1y, size)) {
            if (grid[c1x][c1y] == color && grid[d1x][d1y] == color) {
                return true;
            }
        }
    
        int c2x = a[0] - wx, c2y = a[1] - wy;
        int d2x = b[0] - wx, d2y = b[1] - wy;
        if (inside(c2x, c2y, size) && inside(d2x, d2y, size)) {
            if (grid[c2x][c2y] == color && grid[d2x][d2y] == color) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean inside(int x, int y, int n) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
}
