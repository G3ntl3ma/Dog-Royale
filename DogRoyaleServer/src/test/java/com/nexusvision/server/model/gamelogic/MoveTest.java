package com.nexusvision.server.model.gamelogic;

import static org.mockito.Mockito.*;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MoveTest {

    private Move move;
    private Player mockPlayer;
    private Field mockFromField, mockToField;
    private Card mockCard;
    private Game mockGame;

    @Before
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockFromField = mock(Field.class);
        mockToField = mock(Field.class);
        mockCard = mock(Card.class);
        mockGame = mock(Game.class);

        move = new Move(mockPlayer, mockFromField, mockToField, false, mockCard);
    }

    @Test
    public void testMoveConstructor() {
        assertNotNull(move);
        assertEquals(mockPlayer, move.getPlayer());
        assertEquals(mockFromField, move.getFrom());
        assertEquals(mockToField, move.getTo());
        assertEquals(mockCard, move.getCardUsed());
        assertFalse(move.isSwapMove());
        assertFalse(move.isStartMove());
    }
    @Test
    public void testGetSelectedValue() {
        when(mockToField.getType()).thenReturn(FieldType.HOUSE);
        when(mockFromField.getType()).thenReturn(FieldType.NORMAL);
        when(mockPlayer.getStartField()).thenReturn(mockFromField);
        when(mockFromField.getFieldId()).thenReturn(10);
        when(mockToField.getFieldId()).thenReturn(20);
        when(mockPlayer.getHouseFirstIndex()).thenReturn(5);

        int selectedValue = move.getSelectedValue();
        assertEquals(15, selectedValue); // 10 + 20 - 5 - 10
    }
    @Test
    public void testEqual() {
        Move anotherMove = new Move(mockPlayer, mockFromField, mockToField, false, mockCard);
        assertTrue(move.equal(anotherMove));
    }
    @Test
    public void testExecute() {

        ArrayList<Card> mockCardList = new ArrayList<>();
        mockCardList.add(mockCard);
        when(mockPlayer.getCardList()).thenReturn(mockCardList);

        when(mockGame.getPile()).thenReturn(new ArrayList<>());
        when(mockGame.getDeck()).thenReturn(new ArrayList<>());
        when(mockPlayer.getLastMoveCountFigureMovedIntoHouse()).thenReturn(0);

        UndoMove result = move.execute(mockGame);

        assertNotNull(result);
    }

    @Test
    public void testPrintMove() {
        move.printMove();
        // this test is just make sure print method is work well
    }

    @Test
    public void testGetFieldTypeTo() {
        when(mockToField.getType()).thenReturn(FieldType.HOUSE);
        assertEquals(FieldType.HOUSE, move.getFieldTypeTo());
    }

}
