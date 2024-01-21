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
        // arrange
        Game game = createFakeGame();

        // save the initial state
        SaveState saveState = new SaveState(game);
        SaveState originalState = new SaveState(game);

        // change the state
        modifyGameForTesting(game);

        // use load
        saveState.loadState(game);

        //assert
        assertEquals(originalState, new SaveState(game));
    }

    // build a fake object
    private Game createFakeGame() {

        return new Game("conf", 4, 5, 50, 0);
    }

    // change the state
    private void modifyGameForTesting(Game game) {
        game.setPlayerToMoveId(2);

    }
}