package com.nexusvision.server.model.gamelogic;
import static org.mockito.Mockito.*;

import com.nexusvision.server.model.enums.Card;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class UndoMoveTest {

    private UndoMove undoMove;
    private Move mockMove;
    private Game mockGame;
    private Player mockPlayer, mockOpponent;
    private Field mockFromField, mockToField;
    private Figure mockPlayerFigure, mockOpponentFigure;
    private Card mockPlayerDrawnCard, mockOpponentDrawnCard, mockLastCardOnPile;

    @Before
    public void setUp() {
        // initial
        mockMove = mock(Move.class);
        mockGame = mock(Game.class);
        mockPlayer = mock(Player.class);
        mockOpponent = mock(Player.class);
        mockFromField = mock(Field.class);
        mockToField = mock(Field.class);
        mockPlayerFigure = mock(Figure.class);
        mockOpponentFigure = mock(Figure.class);
        mockPlayerDrawnCard = mock(Card.class);
        mockOpponentDrawnCard = mock(Card.class);
        mockLastCardOnPile = mock(Card.class);

        // assume all objects
        when(mockMove.getPlayer()).thenReturn(mockPlayer);
        when(mockMove.getFrom()).thenReturn(mockFromField);
        when(mockMove.getTo()).thenReturn(mockToField);
        when(mockPlayerFigure.getOwnerId()).thenReturn(0); // 假设玩家 ID 为 0
        when(mockOpponentFigure.getOwnerId()).thenReturn(1); // 假设对手 ID 为 1
        ArrayList<Player> playerList = new ArrayList<>(Arrays.asList(mockPlayer, mockOpponent));
        when(mockGame.getPlayerList()).thenReturn(playerList);

        // make a entity UndoMove
        undoMove = new UndoMove(mockMove, mockPlayerFigure, mockOpponentFigure, mockPlayerDrawnCard, mockOpponentDrawnCard, mockLastCardOnPile, 0);
    }

    @Test
    public void testExecuteForNormalMove() {
        // assume move is normal move
        when(mockMove.isSwapMove()).thenReturn(false);
        when(mockMove.isStartMove()).thenReturn(false);


        undoMove.execute(mockGame);

        verify(mockFromField).setFigure(mockPlayerFigure);
        verify(mockToField).setFigure(mockOpponentFigure);
        verify(mockGame).increaseMovesCounter(-1);
        verify(mockGame).setDrawnCard(mockLastCardOnPile);

    }
    @Test
    public void testExecuteForSwapMove() {
        //assume move is swapmove
        when(mockMove.isSwapMove()).thenReturn(true);

        undoMove.execute(mockGame);

        verify(mockFromField).setFigure(mockPlayerFigure);
        verify(mockToField).setFigure(mockOpponentFigure);

    }
}