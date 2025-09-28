package com.example.web.service;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.game.engine.ComputerEngine;
import com.example.game.model.Board;
import com.example.game.model.GameConstants;
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
            logger.info("Game status: {}", board.getGameStatus());

            String gameStatus = board.getGameStatus();
            String winner = null;
            GameResponse.Move responseMove = null;
            
            if (GameConstants.RUNNING.equals(gameStatus)) {
                ComputerEngine engine = new ComputerEngine();
                Pair<Integer, Integer> move = engine.getMove(board);
                responseMove = new GameResponse.Move(
                    move.getLeft(), 
                    move.getRight(), 
                    request.getNextPlayerColor()
                );
                logger.info("Computer calculated move: x={}, y={}", move.getLeft(), move.getRight());
                
                char playerColor = request.getNextPlayerColor().charAt(0);
                board.makeMove(move.getLeft(), move.getRight(), playerColor);
                
                gameStatus = board.getGameStatus();
            } 
            if (GameConstants.WHITE_WIN.equals(gameStatus) || GameConstants.BLACK_WIN.equals(gameStatus)) {
                winner = String.valueOf(board.getWinner());
            }
            
            return new GameResponse(responseMove, gameStatus, winner);
        } catch (GameValidationException e) {
            logger.error("Incorrect game data: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error calculating move: {}", e.getMessage(), e);
            throw new RuntimeException("Error calculating move: " + e.getMessage());
        }
    }
    
    
    private Board createBoardFromString(String data, int size) {
        logger.debug("Creating board from string: data='{}', size={}", data, size);
        
        if (size < GameConstants.MIN_BOARD_SIZE || size > GameConstants.MAX_BOARD_SIZE) {
            String errorMsg = String.format("Invalid board size: %d. Must be between %d and %d", 
                                          size, GameConstants.MIN_BOARD_SIZE, GameConstants.MAX_BOARD_SIZE);
            logger.error(errorMsg);
            throw new GameValidationException(errorMsg);
        }
        
        int expectedLength = size * size;
        if (data.length() != expectedLength) {
            String errorMsg = String.format("Invalid data length: expected %d characters for %dx%d board, got %d", 
                                          expectedLength, size, size, data.length());
            logger.error(errorMsg);
            throw new GameDataException(errorMsg);
        }
        
        try {
            Board board = new Board(size);
            char[][] grid = new char[size][size];
            
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int index = i * size + j;
                    grid[i][j] = data.charAt(index);
                }
            }
            
            board.setGrid(grid);
            logger.debug("Board created successfully with {}x{} grid", size, size);
            return board;
        } catch (IllegalStateException e) {
            throw new GameValidationException(e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating board from string: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating board from string: " + e.getMessage());
        }
    }
    
}
