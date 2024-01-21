package com.nexusvision.server.model.gamelogic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AiTest {

    @Test
    public void testGetMove() {

        // Arrange
        int numberOfSimulations = 100;
        Game game = Mockito.mock(Game.class);
        when(game.hash()).thenReturn(new ArrayList<>()); // Mock the behavior of game.hash()

        // Act
        Ai ai = new Ai(numberOfSimulations);
        Move move = ai.getMove(game);

        // Assert
        assertNotNull(move);

        // Verify that certain methods of the mock object are called
        verify(game, atLeastOnce()).getCurrentPlayer();
        verify(game, atLeastOnce()).checkGameOver();

    }
}

