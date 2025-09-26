package com.example.game.model;

import org.apache.commons.lang3.tuple.Pair;

import com.example.game.engine.ComputerEngine;

public class Game {
    private final int boardSize;
    private final Player player1;
    private final Player player2;
    private final Board board;
    private String gameStatus;

    public Player winner;

    public static final String RUNNING = "running";
    public static final String DRAW = "draw";
    public static final String WIN = "win";

    private int turn;
    
    public Game(int boardSize, String player1Type, char player1Color, 
                String player2Type, char player2Color) {
        this.boardSize = boardSize;
        this.player1 = new Player(player1Type, player1Color);
        this.player2 = new Player(player2Type, player2Color);
        this.board = new Board(boardSize);
        this.turn = 0;
        this.gameStatus = RUNNING;
    }

    public void makeMove(int x, int y) {
        if(gameStatus.equals(RUNNING)) {
            
            board.makeMove(x, y, getCurrentPlayer().getColor());
            turn++;
            turn = turn % 2;
            if(board.getEmptyCells().isEmpty()) {
                gameStatus = DRAW;
            }
        }
        else {
            throw new IllegalStateException("Game is over");
        }
    }

    public Pair<Integer, Integer> makeComputerMove() {
        Pair<Integer, Integer> move = ComputerEngine.getMove(board);
        makeMove(move.getLeft(), move.getRight());
        return move;
    }

    public Player getCurrentPlayer() {
        return turn == 0 ? player1 : player2;
    }

    public Board getBoard() {
        return board;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public Player getWinner() {
        return winner;
    }
}
