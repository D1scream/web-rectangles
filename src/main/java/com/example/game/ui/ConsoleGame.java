package com.example.game.ui;

import java.util.Scanner;

import org.apache.commons.lang3.tuple.Pair;

import com.example.game.model.Game;
import com.example.game.model.Player;
import com.example.game.service.GameService;

public class ConsoleGame {
    private final Scanner scanner;
    private Game currentGame;
    private final GameService gameService;
    
    public ConsoleGame() {
        this.scanner = new Scanner(System.in);
        this.gameService = new GameService();
    }
    
    public void start() {
        System.out.println("Welcome to Web Rectangles!");
        
        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim().toLowerCase();
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String params = parts.length > 1 ? parts[1] : "";
            
            switch (command) {
                case "exit":
                    System.out.println("Goodbye!");
                    return;
                case "help":
                    showHelp();
                    break;
                case "game":
                    handleGame(params);
                    break;
                case "move":
                    handleMove(params);
                    break;
                case "status":
                    handleStatus();
                    break;
                default:
                    System.out.println("Incorrect command: " + command + ". Type 'help' for available commands.");
                    break;
            }
        }
    }
    
    private void showHelp() {
        System.out.println("Commands:");
        System.out.println("exit - exit program");
        System.out.println("help - show commands");
        System.out.println("game N, U1, U2 - start new game");
        System.out.println("move X, Y - make a move");
        System.out.println("status - show current game status");
    }
    
    private void handleGame(String params) {
        try {
            String[] parts = params.split(",");
            if (parts.length == 3) {
                int n = Integer.parseInt(parts[0].trim());
                
                String[] u1Parts = parts[1].trim().split(" ");
                if (u1Parts.length != 2) {
                    System.out.println("Error: U1 format should be 'TYPE C'");
                    return;
                }
                String u1Type = u1Parts[0];
                char u1Color = u1Parts[1].charAt(0);
                
                String[] u2Parts = parts[2].trim().split(" ");
                if (u2Parts.length != 2) {
                    System.out.println("Error: U2 format should be 'TYPE C'");
                    return;
                }
                String u2Type = u2Parts[0];
                char u2Color = u2Parts[1].charAt(0);
                
                currentGame = gameService.createGame(n, u1Type, u1Color, u2Type, u2Color);
                
                System.out.println("New game started:");
                System.out.println("Board size: " + n);
                System.out.println("Player 1: " + u1Type + " (" + u1Color + ")");
                System.out.println("Player 2: " + u2Type + " (" + u2Color + ")");
                System.out.println(currentGame.getCurrentPlayer() + " turn");
                
                displayBoard();

                Player currentPlayer = currentGame.getCurrentPlayer();
                while (currentPlayer.isComputer() && currentGame.getGameStatus().equals(Game.RUNNING)) {
                    Pair<Integer, Integer> move = currentGame.makeComputerMove();
                    System.out.println(currentPlayer.getColor() + " (" + move.getLeft() + ", " + move.getRight() + ")");
                    displayBoard();
                    currentPlayer = currentGame.getCurrentPlayer();
                }
                if(currentGame.getGameStatus().equals(Game.WIN)) {
                    System.out.println(currentGame.getWinner() + " wins!");
                } else if(currentGame.getGameStatus().equals(Game.DRAW)) {
                    System.out.println("Draw!");
                }
                
            } else {
                System.out.println("Invalid format! Use: game N, U1, U2");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: enter numbers!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayBoard() {
        char[][] board = currentGame.getBoard().getGrid();
        System.out.println("Board:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private void handleMove(String params) {
        if (currentGame == null) {
            System.out.println("No game started. Use 'game' command first.");
            return;
        }
        
        if (!currentGame.getGameStatus().equals(Game.RUNNING)) {
            System.out.println("Game is over. Start a new game.");
            return;
        }
        
        try {
            String[] parts = params.split(",");
            if (parts.length == 2) {
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                System.out.println(currentGame.getCurrentPlayer() + " move: (" + x + ", " + y + ")");
                try {
                    currentGame.makeMove(x, y); 

                    displayBoard();
                    
                    Player currentPlayer = currentGame.getCurrentPlayer();
                    if (currentPlayer.isComputer() && currentGame.getGameStatus().equals(Game.RUNNING)) {
                        Pair<Integer, Integer> move = currentGame.makeComputerMove();
                        System.out.println(currentPlayer.getColor() + " (" + move.getLeft() + ", " + move.getRight() + ")");
                        displayBoard();
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
                if(currentGame.getGameStatus().equals(Game.WIN)) {
                    System.out.println(currentGame.getWinner() + " winner winner chicken dinner!!");
                } else if(currentGame.getGameStatus().equals(Game.DRAW)) {
                    System.out.println("Draw!");
                }
            } else {
                System.out.println("Invalid format! Use: move X, Y");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: enter numbers!");
        } catch (IllegalStateException e) {
            System.out.println("Game is OVER!!!");
        }
    }
    
    private void handleStatus() {
        if (currentGame == null) {
            System.out.println("No game started. Use 'game' command first.");
            return;
        }
        
        System.out.println("Current game status:");
        System.out.println("Status: " + currentGame.getGameStatus());
        System.out.println("Current player: " + currentGame.getCurrentPlayer());
        if (currentGame.getWinner() != null) {
            System.out.println("Winner: " + currentGame.getWinner());
        }
        displayBoard();
    }
    
    
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
