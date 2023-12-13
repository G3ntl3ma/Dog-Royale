package com.example.DogRoyalClient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class GameTest {
    @Mock
    private Game game;
    private String gameId;
    private int timeSeconds;
    private int currentPlayers;
    private int maxPlayers;
    private int fieldSize;
    private int figureCount;
    private int[] drawFields;
    private int[] startFields;
    @Test
    void getGameID() {
        game.setGameID(gameId);
        assertEquals(gameId,game.getGameID());
    }

    @Test
    void setGameID() {
        game.setGameID(gameId);
        assertEquals(gameId,game.getGameID());
    }

    @Test
    void getTimeSeconds() {
        game.setTimeSeconds(timeSeconds);
        assertEquals(timeSeconds,game.getTimeSeconds());
    }

    @Test
    void setTimeSeconds() {
        game.setTimeSeconds(timeSeconds);
        assertEquals(timeSeconds,game.getTimeSeconds());
    }

    @Test
    void getCurrentPlayers() {
        game.setCurrentPlayers(currentPlayers);
        assertEquals(currentPlayers,game.getCurrentPlayers());
    }

    @Test
    void setCurrentPlayers() {
        game.setCurrentPlayers(currentPlayers);
        assertEquals(currentPlayers,game.getCurrentPlayers());
    }

    @Test
    void getMaxPlayers() {
        game.setMaxPlayers(maxPlayers);
        assertEquals(maxPlayers,game.getMaxPlayers());
    }

    @Test
    void setMaxPlayers() {
        game.setMaxPlayers(maxPlayers);
        assertEquals(maxPlayers,game.getMaxPlayers());
    }

    @Test
    void getFieldSize() {
        game.setFieldSize(fieldSize);
        assertEquals(fieldSize,game.getFieldSize());
    }

    @Test
    void setFieldSize() {
        game.setFieldSize(fieldSize);
        assertEquals(fieldSize,game.getFieldSize());
    }

    @Test
    void getFigureCount() {
        game.setFigureCount(figureCount);
        assertEquals(figureCount,game.getFigureCount());
    }

    @Test
    void setFigureCount() {
        game.setFigureCount(figureCount);
        assertEquals(figureCount,game.getFigureCount());
    }

    @Test
    void getDrawFields() {
        game.setDrawFields(drawFields);
        assertEquals(drawFields,game.getDrawFields());
    }

    @Test
    void setDrawFields() {
        game.setDrawFields(this.drawFields);
        assertEquals(this.drawFields,game.getDrawFields());
    }

    @Test
    void getStartFields() {
        game.setStartFields(startFields);
        assertEquals(startFields,game.getStartFields());
    }

    @Test
    void setStartFields() {
        game.setStartFields(startFields);
        assertEquals(startFields,game.getStartFields());
    }
}