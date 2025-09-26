package com.example.game.ui;

import java.util.Scanner;

import com.example.game.model.Game;
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
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        }
    }
    
    private void showHelp() {
        System.out.println("Commands:");
        System.out.println("exit - exit program");
        System.out.println("help - show commands");
        System.out.println("game N, U1, U2 - start game");
        System.out.println("move X, Y - move");
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
                String u1Color = u1Parts[1];
                
                String[] u2Parts = parts[2].trim().split(" ");
                if (u2Parts.length != 2) {
                    System.out.println("Error: U2 format should be 'TYPE C'");
                    return;
                }
                String u2Type = u2Parts[0];
                String u2Color = u2Parts[1];
                
                currentGame = gameService.createGame(n, u1Type, u1Color, u2Type, u2Color);
                
                System.out.println("New game started:");
                System.out.println("Board size: " + n);
                System.out.println("Player 1: " + u1Type + " (" + u1Color + ")");
                System.out.println("Player 2: " + u2Type + " (" + u2Color + ")");
                
                currentGame.start();
                
            } else {
                System.out.println("Invalid format! Use: game N, U1, U2");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: enter numbers!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void handleMove(String params) {
        try {
            String[] parts = params.split(",");
            if (parts.length == 2) {
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                System.out.println("Move to point (" + x + ", " + y + ")");
            } else {
                System.out.println("Invalid format! Use: move X, Y");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: enter numbers!");
        }
    }
    
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
