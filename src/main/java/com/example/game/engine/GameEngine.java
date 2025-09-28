package com.example.game.engine;

import org.apache.commons.lang3.tuple.Pair;

import com.example.game.model.Board;

public interface GameEngine {
    Pair<Integer, Integer> getMove(Board board);
}
