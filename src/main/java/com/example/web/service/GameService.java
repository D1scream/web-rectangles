package com.example.web.service;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.game.engine.ComputerEngine;
import com.example.game.model.Board;
import com.example.web.dto.GameRequest;
import com.example.web.dto.GameResponse;
import com.example.web.exception.GameDataException;
import com.example.web.exception.GameValidationException;

public class GameService {
    
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    
    public GameResponse calculateMove(GameRequest request) {
        logger.info("Calculating move for request: size={}, data={}, nextPlayerColor={}", 
                   request.getSize(), request.getData(), request.getNextPlayerColor());
        
        try {
            Board board = createBoardFromString(request.getData(), request.getSize());
            Pair<Integer, Integer> move = ComputerEngine.getMove(board);
            logger.info("Computer calculated move: x={}, y={}", move.getLeft(), move.getRight());
            
            GameResponse.Move responseMove = new GameResponse.Move(
                move.getLeft(), 
                move.getRight(), 
                request.getNextPlayerColor()
            );
            return new GameResponse(responseMove);
            
        } catch (Exception e) {
            logger.error("Error calculating move: {}", e.getMessage(), e);
            throw new RuntimeException("Error calculating move: " + e.getMessage());
        }
    }
    
    
    private Board createBoardFromString(String data, int size) {
        logger.debug("Creating board from string: data='{}', size={}", data, size);
        
        int expectedLength = size * size;
        if (data.length() != expectedLength) {
            String errorMsg = String.format("Invalid data length: expected %d characters for %dx%d board, got %d", 
                                          expectedLength, size, size, data.length());
            logger.error(errorMsg);
            throw new GameDataException(errorMsg);
        }
        
        try {
            Board board = new Board(size);
            char[][] grid = board.getGrid();
            
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int index = i * size + j;
                    char cell = data.charAt(index);
                    if (cell != '.' && cell != 'w' && cell != 'b') {
                        throw new GameValidationException(String.format("Invalid character: '%c'. Only '.' (empty), 'w' (white), and 'b' (black) are allowed", cell));
                    }
                    grid[i][j] = cell;

                }
            }
            
            board.setGrid(grid);
            logger.debug("Board created successfully with {}x{} grid", size, size);
            return board;
        } catch (Exception e) {
            logger.error("Error creating board from string: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating board from string: " + e.getMessage());
        }
    }
    
}
