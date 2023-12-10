package com.example.myapplication;

import static org.junit.jupiter.api.Assertions.*;

import com.example.myapplication.GameInformationClasses.Color;
import com.example.myapplication.GameInformationClasses.DrawCardFields;
import com.example.myapplication.GameInformationClasses.Observer;
import com.example.myapplication.GameInformationClasses.PlayerOrder;
import com.example.myapplication.GameInformationClasses.StartFields;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import java.util.List;

class GameInformationTest {
    @Mock
    private Integer playerCount;
    private Integer fieldsize;
    private Integer figuresPerPlayer;
    private List<Color> colors;
    private DrawCardFields drawCardFields;
    private StartFields startFields;
    private Integer initialCardsPerPlayer;
    private PlayerOrder playerOrder;
    private List<Observer> observer;
    private Integer thinkTimePerMove;
    private Integer visualizationTimePerMove;
    private Integer consequencesForInvalidMove;
    private Integer maximumGameDuration;
    private Integer maximumTotalMoves;
    @Test
    void setPlayerCount() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setPlayerCount(playerCount);
        assertEquals(playerCount,GameInformation.getPlayerCount());
    }

    @Test
    void setFieldsize() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setFieldsize(fieldsize);
        assertEquals(fieldsize,GameInformation.getFieldsize());
    }

    @Test
    void setFiguresPerPlayer() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setFiguresPerPlayer(figuresPerPlayer);
        assertEquals(figuresPerPlayer,GameInformation.getFiguresPerPlayer());
    }

    @Test
    void setColors() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setColors(colors);
        assertEquals(colors,GameInformation.getColors());
    }

    @Test
    void setDrawCardFields() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setDrawCardFields(drawCardFields);
        assertEquals(drawCardFields,GameInformation.getDrawCardFields());
    }

    @Test
    void setStartFields() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setStartFields(startFields);
        assertEquals(startFields,GameInformation.getStartFields());
    }

    @Test
    void setInitialCardsPerPlayer() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setInitialCardsPerPlayer(initialCardsPerPlayer);
        assertEquals(initialCardsPerPlayer,GameInformation.getInitialCardsPerPlayer());
    }

    @Test
    void setPlayerOrder() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setPlayerOrder(playerOrder);
        assertEquals(playerOrder,GameInformation.getPlayerOrder());
    }

    @Test
    void setObserver() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setObserver(observer);
        assertEquals(observer,GameInformation.getObserver());
    }

    @Test
    void setThinkTimePerMove() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setThinkTimePerMove(thinkTimePerMove);
        assertEquals(thinkTimePerMove,GameInformation.getThinkTimePerMove());
    }

    @Test
    void setVisualizationTimePerMove() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setVisualizationTimePerMove(visualizationTimePerMove);
        assertEquals(visualizationTimePerMove,GameInformation.getVisualizationTimePerMove());
    }

    @Test
    void setConsequencesForInvalidMove() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setConsequencesForInvalidMove(consequencesForInvalidMove);
        assertEquals(consequencesForInvalidMove,GameInformation.getConsequencesForInvalidMove());
    }

    @Test
    void setMaximumGameDuration() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setMaximumGameDuration(maximumGameDuration);
        assertEquals(maximumGameDuration,GameInformation.getMaximumGameDuration());
    }

    @Test
    void setMaximumTotalMoves() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setMaximumTotalMoves(maximumTotalMoves);
        assertEquals(maximumTotalMoves,GameInformation.getMaximumTotalMoves());
    }

    @Test
    void getPlayerCount() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setPlayerCount(playerCount);
        assertEquals(playerCount,GameInformation.getPlayerCount());
    }

    @Test
    void getFieldsize() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setFieldsize(fieldsize);
        assertEquals(fieldsize,GameInformation.getFieldsize());
    }

    @Test
    void getFiguresPerPlayer() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setFiguresPerPlayer(figuresPerPlayer);
        assertEquals(figuresPerPlayer,GameInformation.getFiguresPerPlayer());
    }

    @Test
    void getColors() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setColors(colors);
        assertEquals(colors,GameInformation.getColors());
    }

    @Test
    void getDrawCardFields() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setDrawCardFields(drawCardFields);
        assertEquals(drawCardFields,GameInformation.getDrawCardFields());
    }

    @Test
    void getStartFields() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setStartFields(startFields);
        assertEquals(startFields,GameInformation.getStartFields());
    }

    @Test
    void getInitialCardsPerPlayer() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setInitialCardsPerPlayer(initialCardsPerPlayer);
        assertEquals(initialCardsPerPlayer,GameInformation.getInitialCardsPerPlayer());
    }

    @Test
    void getPlayerOrder() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setPlayerOrder(playerOrder);
        assertEquals(playerOrder,GameInformation.getPlayerOrder());
    }

    @Test
    void getObserver() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setObserver(observer);
        assertEquals(observer,GameInformation.getObserver());
    }

    @Test
    void getThinkTimePerMove() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setThinkTimePerMove(thinkTimePerMove);
        assertEquals(thinkTimePerMove,GameInformation.getThinkTimePerMove());
    }

    @Test
    void getVisualizationTimePerMove() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setVisualizationTimePerMove(visualizationTimePerMove);
        assertEquals(visualizationTimePerMove,GameInformation.getVisualizationTimePerMove());
    }

    @Test
    void getConsequencesForInvalidMove() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setConsequencesForInvalidMove(consequencesForInvalidMove);
        assertEquals(consequencesForInvalidMove,GameInformation.getConsequencesForInvalidMove());
    }

    @Test
    void getMaximumGameDuration() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setMaximumGameDuration(maximumGameDuration);
        assertEquals(maximumGameDuration,GameInformation.getMaximumGameDuration());
    }

    @Test
    void getMaximumTotalMoves() {
        PowerMockito.mockStatic(GameInformation.class);
        GameInformation.setMaximumTotalMoves(maximumTotalMoves);
        assertEquals(maximumTotalMoves,GameInformation.getMaximumTotalMoves());
    }
}