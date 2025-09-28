package com.example.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Board {
    private char[][] grid;
    private String gameStatus = GameConstants.RUNNING;
    private char winner;
    private final WinChecker winChecker;
    public Board(int size) {
        GameValidator.validateBoardSize(size);
        this.grid = new char[size][size];
        this.winChecker = new WinChecker();
        initializeEmptyBoard();
    }
    
    private void initializeEmptyBoard() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                grid[i][j] = GameConstants.EMPTY_CELL;
            }
        }
    }

    public List<Pair<Integer, Integer>> getEmptyCells() {
        List<Pair<Integer, Integer>> emptyCells = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (grid[i][j] == GameConstants.EMPTY_CELL) {
                    emptyCells.add(Pair.of(i, j));
                }
            }
        }
        return emptyCells;
    }

    public void makeMove(int x, int y, char color) {
        if (!gameStatus.equals(GameConstants.RUNNING)) {
            throw new IllegalStateException("Game is over");
        }
        
        GameValidator.validateMove(x, y, getSize());
        GameValidator.validateCellNotOccupied(grid[x][y], x, y);
        GameValidator.validateColor(color);
        
        grid[x][y] = color;
        checkGameStatus();
    }

    public Character checkWin() {
        return winChecker.checkWin(grid, getSize());
    }
    
    public boolean checkDraw() {
        return getEmptyCells().isEmpty();
    }
    
    public void checkGameStatus() {
        Character winColor = checkWin();
        if (winColor != null) {
            if (winColor == GameConstants.WHITE_PIECE) {
                gameStatus = GameConstants.WHITE_WIN;
            } else {
                gameStatus = GameConstants.BLACK_WIN;
            }
            winner = winColor;
        } else if (checkDraw()) {
            gameStatus = GameConstants.DRAW;
        } else {
            gameStatus = GameConstants.RUNNING;
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
        return getPieceCount(GameConstants.WHITE_PIECE);
    }
    
    public int getBlackCount() {
        return getPieceCount(GameConstants.BLACK_PIECE);
    }
    
    private int getPieceCount(char color) {
        int count = 0;
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (grid[i][j] == color) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public char getCurrentPlayerColor() {  
        if (getWhiteCount() > getBlackCount()) {
            return GameConstants.BLACK_PIECE;
        }
        
        return GameConstants.WHITE_PIECE;
    }
    
}