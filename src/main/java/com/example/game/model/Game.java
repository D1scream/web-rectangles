package com.example.game.model;

import org.apache.commons.lang3.tuple.Pair;

import com.example.game.engine.ComputerEngine;

public class Game {
    private final int boardSize;
    private final Player player1;
    private final Player player2;
    private final Board board;

    private int turn;
    
    public Game(int boardSize, String player1Type, char player1Color, 
                String player2Type, char player2Color) {
        this.boardSize = boardSize;
        this.player1 = new Player(player1Type, player1Color);
        this.player2 = new Player(player2Type, player2Color);
        this.board = new Board(boardSize);
        this.turn = 0;
    }
    
    public void start() {
        System.out.println("Game started!");
        System.out.println("Board size: " + boardSize);
        System.out.println("Player 1: " + player1);
        System.out.println("Player 2: " + player2);
        
    }

    public void makeMove(int x, int y, char color) {
        if(board.makeMove(x, y, color)) {
            turn++;
            turn = turn % 2;
        }
        Player currentPlayer = turn == 0 ? player1 : player2;

        if (turn == 0 && player1.isComputer() || turn == 1 && player2.isComputer()) {
            Pair<Integer, Integer> move = ComputerEngine.getMove(board);
            System.out.println("Computer " + currentPlayer.getColor() + " move: " + move);
        }
    }

    public void displayBoard() {
        board.display();
    }
}
