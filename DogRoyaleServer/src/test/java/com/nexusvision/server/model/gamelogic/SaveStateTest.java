package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveStateTest {

    @Test
    void testLoadPlayer() {
        // build a player object
        Player player = mock(Player.class);

        // set some mock
        when(player.isOutThisRound()).thenReturn(true);
        when(player.getFiguresInBank()).thenReturn(2);
        when(player.getFiguresInHouse()).thenReturn(1);
        when(player.getLastMoveCountFigureMovedIntoHouse()).thenReturn(10);

        // build a player state
        SaveState.PlayerState playerState = new SaveState.PlayerState(player);

        // load player
        playerState.loadPlayer(player);

        // assert
        assertEquals(true, player.isOutThisRound());
        assertEquals(2, player.getFiguresInBank());
        assertEquals(1, player.getFiguresInHouse());
        assertEquals(10, player.getLastMoveCountFigureMovedIntoHouse());
    }
    @Test
        public void testLoadState() {
            // Arrange
            // build save state object
            SaveState saveState = createFakeSaveState();

            // build new game object
            Game game = createFakeGame();

            // save current game state
            SaveState originalState = new SaveState(game);

            // Act
            saveState.loadState(game);

            // Assert
            assertAll(
                    () -> assertEquals(originalState.getPlayerToStartColor(), game.getPlayerToStartColor()),
                    () -> assertEquals(originalState.getPlayerToMoveId(), game.getPlayerToMoveId()),
                    () -> assertEquals(originalState.getMovesMade(), game.getMovesMade()),
                    () -> assertEquals(originalState.getPlayersRemaining(), game.getPlayersRemaining()),
                    () -> assertEquals(originalState.getRound(), game.getRound()),
                    () -> assertEquals(originalState.isFirstMoveOfRound(), game.isFirstMoveOfRound()),
                    () -> assertArrayEquals(originalState.getDeck().toArray(), game.getDeck().toArray()),
                    () -> assertArrayEquals(originalState.getPile().toArray(), game.getPile().toArray())
            );
        }

        // moca a savestate object
        private SaveState createFakeSaveState() {

            return new SaveState(createFakeGame());
        }

        // mock a fake game object
        private Game createFakeGame() {

            return new Game("conf", 4, 5, 50, 0);
        }
}