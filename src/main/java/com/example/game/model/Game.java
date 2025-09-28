package com.example.game.model;

import org.apache.commons.lang3.tuple.Pair;

import com.example.game.engine.ComputerEngine;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Board board;

    public Game(int boardSize, String player1Type, char player1Color, 
                String player2Type, char player2Color) {
        this.player1 = new Player(player1Type, player1Color);
        this.player2 = new Player(player2Type, player2Color);
        this.board = new Board(boardSize);
    }

    public void makeMove(int x, int y) {
        char color = getCurrentPlayer().getColor();
        try {
            board.makeMove(x, y, color);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid move: " + e.getMessage());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Game is over");
        }
    }

    public Pair<Integer, Integer> makeComputerMove() {
        Pair<Integer, Integer> move = ComputerEngine.getMove(board);
        makeMove(move.getLeft(), move.getRight());
        return move;
    }

    public Player getCurrentPlayer() {
        if(board.getCurrentPlayerColor() == player1.getColor()) {
            return player1;
        } else {
            return player2;
        }
    }

    public Board getBoard() {
        return board;
    }

    public String getGameStatus() {
        return board.getGameStatus();
    }
}