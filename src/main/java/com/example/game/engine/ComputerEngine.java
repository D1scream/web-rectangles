package com.example.game.engine;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import com.example.game.model.Board;

public class ComputerEngine { 

    public static Pair<Integer, Integer> getMove(Board board) {
        List<Pair<Integer, Integer>> emptyCells = board.getEmptyCells();
        if (emptyCells.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }
}
