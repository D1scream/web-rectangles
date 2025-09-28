package com.example.game.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.example.game.engine.ComputerEngine;

public class Game {
    private final Map<Character, Set<Integer>> playerPoints = new HashMap<>();
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
        this.player1 = new Player(player1Type, player1Color);
        this.player2 = new Player(player2Type, player2Color);
        this.board = new Board(boardSize);
        this.turn = 0;
        this.gameStatus = RUNNING;
    }

    public void makeMove(int x, int y) {
        if(gameStatus.equals(RUNNING)) {
            char color = getCurrentPlayer().getColor();
            try {
                board.makeMove(x, y, color);
                playerPoints.computeIfAbsent(color,k->new HashSet<>()).add(encode(x,y));
                checkGameStatus();
                turn++;
                turn = turn % 2;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid move: " + e.getMessage());
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

    // x, y - last move
    public boolean checkWin(int x, int y, char color) {
        Set<Integer> pts = playerPoints.getOrDefault(color, Collections.emptySet());

        for (int code: pts) {
            int[] p = decode(code);
            if (p[0]==x && p[1]==y) continue;
            int vx = p[0]-x, vy = p[1]-y;
            int wx = -vy, wy = vx;

            int[] c1 = {p[0]+wx, p[1]+wy};
            int[] d1 = {x+wx, y+wy};
            if (pts.contains(encode(c1[0],c1[1])) && pts.contains(encode(d1[0],d1[1]))) return true;

            int[] c2 = {p[0]-wx, p[1]-wy};
            int[] d2 = {x-wx, y-wy};
            if (pts.contains(encode(c2[0],c2[1])) && pts.contains(encode(d2[0],d2[1]))) return true;
        }
        return false;
    }

    private static int encode(int x,int y){ return (x << 16) | y; }
    private static int[] decode(int code){ return new int[]{code >> 16, code & 0xFFFF}; }
    

    public boolean checkDraw() {
        return board.getEmptyCells().isEmpty();
    }

    public void checkGameStatus() {
        if(checkWin(board.getLastMove().getLeft(), board.getLastMove().getRight(), getCurrentPlayer().getColor())) {
            gameStatus = WIN;
            winner = getCurrentPlayer();
        }
        else if(checkDraw()) {
            gameStatus = DRAW;
        }
        else {
            gameStatus = RUNNING;
        }
    }
}
