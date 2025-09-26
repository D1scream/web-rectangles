package com.example;

import java.util.Scanner;

import com.example.game.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Web Rectangles!");
        
        try (Scanner scanner = new Scanner(System.in)) {
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
                        System.out.println("Commands:");
                        System.out.println("exit - exit program");
                        System.out.println("help - show commands");
                        System.out.println("game N, U1, U2 - start game");
                        System.out.println("move X, Y - move");
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
    }
    
    private static void handleGame(String params) {
        try {
            String[] parts = params.split(",");
            if (parts.length == 3) {
                int n = Integer.parseInt(parts[0].trim());
                if (n <= 2) {
                    System.out.println("Error: board size must be > 2");
                    return;
                }
                
                // Parse player 1 parameters
                String[] u1Parts = parts[1].trim().split(" ");
                if (u1Parts.length != 2) {
                    System.out.println("Error: U1 format should be 'TYPE C'");
                    return;
                }
                String u1Type = u1Parts[0];
                String u1Color = u1Parts[1];
                
                // Parse player 2 parameters
                String[] u2Parts = parts[2].trim().split(" ");
                if (u2Parts.length != 2) {
                    System.out.println("Error: U2 format should be 'TYPE C'");
                    return;
                }
                String u2Type = u2Parts[0];
                String u2Color = u2Parts[1];
                
                // Validate parameters
                if (!u1Type.equals("user") && !u1Type.equals("comp") || 
                    !u2Type.equals("user") && !u2Type.equals("comp")) {
                    System.out.println("Error: player type must be 'user' or 'comp'");
                    return;
                }
                
                if (!u1Color.equals("w") && !u1Color.equals("b") || 
                    !u2Color.equals("w") && !u2Color.equals("b")) {
                    System.out.println("Error: color must be 'w' or 'b'");
                    return;
                }
                
                if (u1Color.equals(u2Color)) {
                    System.out.println("Error: players must have different colors");
                    return;
                }
                
                System.out.println("New game started:");
                System.out.println("Board size: " + n);
                System.out.println("Player 1: " + u1Type + " (" + u1Color + ")");
                System.out.println("Player 2: " + u2Type + " (" + u2Color + ")");
                
                Game game = new Game(n, u1Type, u1Color, u2Type, u2Color);
                game.start();
            } else {
                System.out.println("Invalid format! Use: game N, U1, U2");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: enter numbers!");
        }
    }
    
    private static void handleMove(String params) {
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
}