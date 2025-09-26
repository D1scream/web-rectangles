package com.example.game;

import lombok.Getter;

@Getter
public class Game {
    private final int boardSize;
    private final Player player1;
    private final Player player2;
    
    public Game(int boardSize, String player1Type, String player1Color, 
                String player2Type, String player2Color) {
        this.boardSize = boardSize;
        this.player1 = new Player(player1Type, player1Color);
        this.player2 = new Player(player2Type, player2Color);
    }
    
    public void start() {
        System.out.println("Game started!");
        System.out.println("Board size: " + boardSize);
        System.out.println("Player 1: " + player1);
        System.out.println("Player 2: " + player2);

        Board board = new Board(boardSize);
        board.display();
        
        System.out.println("Game finished.");
    }
}