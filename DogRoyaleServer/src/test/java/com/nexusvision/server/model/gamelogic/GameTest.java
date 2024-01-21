package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    public void testIncreaseMovesCounter() {
        // Arrange
        Game game = new Game("conf", 4, 5, 50, 0);

        // Act
        game.increaseMovesCounter(2);
        int movesAfterIncrease = game.getMovesMade();

        // Assert
        assertEquals(2, movesAfterIncrease);
    }

    @Test
    public void testReshuffle() {
        // Arrange
        Game game = new Game("conf", 4, 5, 50, 0);
        game.getDeck().clear();
        game.getPile().clear();
        game.getDeck().add(Card.card2);
        game.getDeck().add(Card.card3);
        game.getPile().add(Card.card8);

        // Act
        game.reshuffle();
        int deckSizeAfterReshuffle = game.getDeck().size();
        int pileSizeAfterReshuffle = game.getPile().size();

        // Assert
        assertEquals(3, deckSizeAfterReshuffle); // 2 cards from the original deck and 1 from the pile
        assertEquals(0, pileSizeAfterReshuffle); // Pile should be empty after reshuffling
    }

    @Test
    void testReInit() {
        //Arrange
        Game game = new Game("conf", 4, 5, 50, 0);

        game.reInit();

        // Assert
        assertEquals(0, game.getPlayersRemaining()); // 确保 playersRemaining 被正确设置为 0
        assertEquals(1, game.getRound()); // 确保 round 被递增

    }

    @Test
    void testDiscardHandCards() {
        // Arrange
        Game game = new Game("conf", 4, 5, 50, 0);
        //set initial data
        int initialCardsPerPlayer = 5;
        game.getPlayerList().add(new Player(1,5));
        game.getPlayerList().add(new Player(2,5));

        // set card numbers
        int initialHandSize = game.getCurrentPlayer().getCardList().size();


        ArrayList<Card> discardedCards = game.discardHandCards();


        assertEquals(initialHandSize, discardedCards.size()); // 确保返回的卡片数量与初始手牌数量相同
        assertTrue(game.getCurrentPlayer().getCardList().isEmpty()); // 确保当前玩家手牌为空
        assertEquals(discardedCards, game.getPile()); // 确保被丢弃的卡片已添加到堆叠中


    }

    void testDistributeCards() {
        // arrange
        Game game = new Game("conf", 4, 5, 50, 0);

        // set initial data
        int initialCardsPerPlayer = 5;
        game.getPlayerList().add(new Player(1,5));
        game.getPlayerList().add(new Player(2,5));

        // set current player
        game.setCurrentPlayer(game.getPlayerList().get(0));

        // get the card number
        int initialHandSize = game.getCurrentPlayer().getCardList().size();

        // use distributeCards
        game.distributeCards();

        // assert
        int expectedHandSize = initialHandSize + game.getInitialCardsPerPlayer() * game.getPlayerList().size();
        assertEquals(expectedHandSize, game.getCurrentPlayer().getCardList().size());
        assertTrue(game.isFirstMoveOfRound());
    }
    @Test
    void testPrintTotalCards() {
        // set output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // arrange
        Game game = new Game("conf", 4, 5, 50, 0);
        game.initDeck();
        game.printTotalCards();


        String capturedOutput = outContent.toString().trim();


        String expectedOutput = "total cards in game 110 deck ";

        // assert
        assertTrue(capturedOutput.contains(expectedOutput));

        // chang it back
        System.setOut(System.out);
    }
    @Test
    void testCompare() {
        // arrange
        Player player1 = new Player(1,5);
        Player player2 = new Player(2,5);

        // set figuresInHouse and lastMoveCountFigureMovedIntoHouse
        player1.setFiguresInHouse(3);
        player1.setLastMoveCountFigureMovedIntoHouse(5);

        player2.setFiguresInHouse(2);
        player2.setLastMoveCountFigureMovedIntoHouse(4);

        // arrange
        Game game = new Game("conf", 4, 5, 50, 0);

        // use compare
        int result = game.compare(player1, player2);

        // compare result
        int expectedResult = 1;

        //assert
        assertEquals(expectedResult, result);
    }
    @Test
    void testRoundOver() {
        // arrange
        Game game = new Game("conf", 4, 5, 50, 0);

        // set Remain
        game.setPlayersRemaining(2);

        // use reround
        assertFalse(game.roundOver());

        // set Remaining
        game.setPlayersRemaining(0);

        // use roundover
        assertTrue(game.roundOver());
    }

    @Test
    void testHandleIllegalMoveKickFromGame() {
        // Arrange
        Game game = new Game("conf", 4, 5, 50, 0);

        // Mock
        Player mockPlayer = mock(Player.class);
        when(game.getCurrentPlayer()).thenReturn(mockPlayer);

        // simulate handleIllegalMove
        Game spyGame = spy(game);

        // Act
        spyGame.handleIllegalMove();

        // Assert
        verify(spyGame, times(1)).excludeFromGame(mockPlayer);
    }
    @Test
    public void testRemovePlayerFromBoard() {
        // arrange
        Game game = new Game("conf", 4, 5, 50, 0);

        // mock player
        Player mockPlayer = mock(Player.class);

        // mock a expected value
        when(mockPlayer.isOutThisRound()).thenReturn(true);

        // add it to list
        game.getPlayerList().add(mockPlayer);


        game.removePlayerFromBoard(mockPlayer);

        //assert
        for (Figure figure : mockPlayer.getFigureList()) {
            verify(figure).setField(null);
        }

        //assert
        verify(mockPlayer).setOutThisRound(true);
    }
}
