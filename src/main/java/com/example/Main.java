package com.example;

import com.example.game.ui.ConsoleGame;

public class Main {
    public static void main(String[] args) {
        ConsoleGame consoleGame = new ConsoleGame();
        try {
            consoleGame.start();
        } finally {
            consoleGame.close();
        }
    }
}