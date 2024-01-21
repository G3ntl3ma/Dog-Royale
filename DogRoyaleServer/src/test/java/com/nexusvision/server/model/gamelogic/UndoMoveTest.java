package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


    public class UndoMoveTest {

        @Test
        public void testExecute() {
            // create game object
            Game game = new Game("conf", 4, 5, 50, 0);

            // create 2 players stand for player and the ai
            Player player = new Player(1, 4);
            Player opponent = new Player(2, 4);

            // create 2 field
            Field startField = new Field(1, FieldType.START);
            Field houseField = new Field(4, FieldType.HOUSE);

            // create a card
            Card card = Card.card2;

            // create move
            Move move = new Move(player, startField, houseField, false, card);

            // run move and get undomove
            UndoMove undoMove = move.execute(game);

            // do record
            int playerFiguresInBankBefore = player.getFiguresInBank();
            int opponentFiguresInBankBefore = opponent.getFiguresInBank();
            int playerFiguresInHouseBefore = player.getFiguresInHouse();

            // run execute
            undoMove.execute(game);

            // assert
            assertEquals(playerFiguresInBankBefore, player.getFiguresInBank());
            assertEquals(opponentFiguresInBankBefore, opponent.getFiguresInBank());
            assertEquals(playerFiguresInHouseBefore, player.getFiguresInHouse());

            // assert
            assertTrue(player.getCardList().contains(card));
            assertFalse(game.getPile().contains(card));

            // assert
            assertEquals(startField.getFigure(), player.getStartField().getFigure());
        }
}
