package com.nexusvision.server.model.gamelogic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.lang.Math;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testGetFirstOnBench() {
        // build a player object
        Player player = new Player(1, 4);

        // move the first Figure on Bench
        player.getFigureList().get(0).setOnBench(true);

        // call FirstonBench
        Figure firstOnBench = player.getFirstOnBench();

        // assert
        assertNotNull(firstOnBench);
        assertTrue(firstOnBench.isOnBench());
    }
    @Test
    public void testGetCardListInteger() {
        // build a player object
        Player player = new Player(1, 4);

        // add card
        player.getCardList().add(Card.card2);
        player.getCardList().add(Card.card3);
        player.getCardList().add(Card.card5);

        // use get
        ArrayList<Integer> cardListInteger = player.getCardListInteger();

        // assert
        assertNotNull(cardListInteger);
        assertTrue(cardListInteger.contains(Card.card2.getOrdinal()));
        assertTrue(cardListInteger.contains(Card.card3.getOrdinal()));
        assertTrue(cardListInteger.contains(Card.card5.getOrdinal()));
    }
    @Test
    public void testPrintInfo() {
        // build a player object
        Player player = new Player(1, 4);
        player.setFiguresInBank(2);
        player.setFiguresInHouse(1);
        player.setOutThisRound(true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // call printInfo
        player.printInfo();

        // make system back
        System.setOut(System.out);

        // assert
        String output = outputStream.toString();
        assertTrue(output.contains("player 1 figBank 2 figs in house 1 out this round true"));
    }
    @Test
    public void testPrintHouse() {
        // build a player object
        Player player = new Player(1, 4);

        // build Figure
        Figure figure1 = new Figure(1, 0);
        Figure figure2 = new Figure(1, 1);
        Figure figure3 = new Figure(1, 2);

        Field houseField = new Field(1, FieldType.HOUSE);
        houseField.setFigure(figure1);
        houseField.setNext(new Field(2, FieldType.HOUSE));
        houseField.getNext().setFigure(figure2);
        houseField.getNext().setNext(new Field(3, FieldType.HOUSE));
        houseField.getNext().getNext().setFigure(figure3);

        player.setStartField(new Field(0, FieldType.START));
        player.getStartField().setHouse(houseField);

        // set a systemoutput
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // call print house
        player.printHouse();

        // make systemoutput back
        System.setOut(System.out);

        // assert
        String output = outputStream.toString();
        assertFalse(output.contains("house: 1-2-3-"));
    }
    @Test
    void testPrintCards() {
        // build a player object
        Player player = new Player(1, 4);

        // make sure card list is empty
        assertTrue(player.getCardList().isEmpty());

        // add some card
        player.getCardList().add(Card.card2);
        player.getCardList().add(Card.card3);
        player.getCardList().add(Card.card5);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // call printcard
        player.printCards();

        // make systemoutput back
        System.setOut(System.out);

        // assert
        assertEquals("card2 card3 card5", outputStream.toString().trim());
    }
    @Test
    void testSetExclude() {
        // build a player object
        Player player = new Player(1, 4);

        // make sure player has noot been excluded
        assertFalse(player.isExcluded());

        // call exclude
        player.setExclude();

        // assert
        assertTrue(player.isExcluded());
    }
    @Test
    void testSetOutThisRound() {
        // build a player object
        Player player = new Player(1, 4);

        // call setoutthisround
        player.setOutThisRound();

        // assert
        assertTrue(player.isOutThisRound());
    }
    @Test
    void testGenerateMoves() {
        // build game object
        Game game = new Game("conf", 4, 5, 50, 0);

        // build player object
        Player player = new Player(1, 4);

        // add some card to plaer
        player.getCardList().add(Card.card5);
        player.getCardList().add(Card.card2);

        // call generatemoves
        ArrayList<Move> moves = player.generateMoves(game);

        // assert
        assertNotNull(moves);
        assertTrue(moves.isEmpty());
    }
}
