package com.nexusvision.server.model.gamelogic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.service.CardService;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class GameTest {

    private Game game;
    private Player mockPlayer;
    private Field mockField;
    private CardService mockCardService;
    private ArrayList<Player> mockPlayerList;
    private ArrayList<Card> mockDeck;
    private ArrayList<Card> mockPile;

    @Before
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockField = mock(Field.class);
        mockCardService = mock(CardService.class);
        mockPlayerList = new ArrayList<>();
        mockDeck = new ArrayList<>();
        mockPile = new ArrayList<>();
        mockPlayerList.add(mockPlayer);

        game = new Game("conf", 4, 5, 100, 0); // 调整参数以匹配你的构造函数
        game.setCardService(mockCardService);
        game.setPlayerList(mockPlayerList);
        game.setDeck(mockDeck);
        game.setPile(mockPile);
    }

    @Test
    public void testIncreaseMovesCounter() {
        int initialMoves = game.getMovesMade();
        game.increaseMovesCounter(1);
        assertEquals(initialMoves + 1, game.getMovesMade());
    }
    @org.junit.jupiter.api.Test
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
    public void testNextPlayer() {
        // add new player
        Player anotherMockPlayer = mock(Player.class);
        game.getPlayerList().add(anotherMockPlayer);

        game.nextPlayer();

        // assert if it is in nextplayer
        assertEquals(0, game.getPlayerToMoveId());
    }
    @Test
    public void testGetWinnerOrder() {
        // mock some players with different notes
        when(mockPlayer.getFiguresInHouse()).thenReturn(3);
        Player anotherMockPlayer = mock(Player.class);
        when(anotherMockPlayer.getFiguresInHouse()).thenReturn(4);
        game.getPlayerList().add(anotherMockPlayer);

        ArrayList<Integer> winnerOrder = game.getWinnerOrder();

        // assert
        assertEquals(anotherMockPlayer.getPlayerId(), winnerOrder.get(0).intValue());
        assertEquals(mockPlayer.getPlayerId(), winnerOrder.get(1).intValue());
    }
    @org.junit.jupiter.api.Test
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


        assertEquals(initialHandSize, discardedCards.size()); //make  sure the card number is same
        assertTrue(game.getCurrentPlayer().getCardList().isEmpty()); // make sure the card number is 0 in playershand
        assertEquals(discardedCards, game.getPile()); // Ensure that the discarded cards have been added to the deck.
    }
    @Test
    public void testShuffleUnknown() {
        Player mockOpponent = mock(Player.class);
        game.getPlayerList().add(mockOpponent);

        game.shuffleUnknown(mockPlayer);

        // assert
        verify(mockOpponent, never()).getCardList();
        verify(mockPlayer, never()).getCardList();
    }
    @Test
    public void testReInit() {
        game.reInit();

        assertEquals(1, game.getRound()); // assume it is get higher
        assertEquals(0, game.getPlayerToMoveId()); // assume PlayerToMoveId is first player
        // 其他相关状态检查
    }
    @Test
    public void testDistributeCards() {
        game.initDeck(); // intial
        game.distributeCards();

        for (Player player : game.getPlayerList()) {
            assertEquals(0, player.getCardList().size());
        }
    }
    @org.junit.jupiter.api.Test
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
    @org.junit.jupiter.api.Test
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
    public void testCheckGameOver() {
        // noone is win by the condition
        assertFalse(game.checkGameOver());

        // only one player is wining
        when(mockPlayer.getFiguresInHouse()).thenReturn(game.getFiguresPerPlayer());
        assertTrue(game.checkGameOver());
    }
    @Test
    public void testExcludeFromRoundAndGame() {
        // test exclude player from round
        game.excludeFromRound(mockPlayer);
        assertFalse(mockPlayer.isOutThisRound());
        assertEquals(game.getPlayersRemaining(), game.getPlayersRemaining());

        // test exclude player from Game
        game.excludeFromGame(mockPlayer);
        assertFalse(mockPlayer.isExcluded());
        assertEquals(game.getPlayersRemaining() , game.getPlayersRemaining());
    }
    @Test
    public void testDistributeCardsDifferentScenarios() {
        game.initDeck();
        game.distributeCards();

        for (Player player : game.getPlayerList()) {
            assertEquals(0, player.getCardList().size());
        }

        // add more players
        Player anotherMockPlayer = mock(Player.class);
        game.getPlayerList().add(anotherMockPlayer);
        game.distributeCards();

        // assert if everyone has hisown card
        for (Player player : game.getPlayerList()) {
            assertEquals(0, player.getCardList().size());
        }
    }
}
