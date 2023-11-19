package org.example;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoveTest {
    MoveTest() {
    }

    @Test
    void equal() {
        Player player = new Player(0, 2);
        Card card1 = new Card(CardType.MAGNET);
        Card card2 = new Card(CardType.MAGNET);
        Card card3 = new Card(CardType.COPY);
        Move move1 = new Move(player, card1);
        Move move2 = new Move(player, card2);
        Move move3 = new Move(player, card3);
        Assertions.assertEquals(true, move1.equal(move2));
        Assertions.assertEquals(false, move1.equal(move3));
        Move empty1 = new Move((Player)null, (Card)null);
        Move empty2 = new Move((Player)null, (Card)null);
        Assertions.assertEquals(true, empty1.equal(empty2));
    }

    @Test
    void noBackwardsInHouse() {
        String conf = "snnnnknnnnsnnnnknnnn";
        int figureCount = 2;
        int maxMoves = 10000000;
        int handCardCount = 10;
        Game game = new Game(conf, figureCount, handCardCount, maxMoves);
        game.initDeck();
        game.distributeCards();
        Player player = (Player)game.players.get(0);
        Figure fig = (Figure)player.figures.get(0);
        Move start = new Move(player, (Card)null);
        start.execute(game);
        Field from = game.getField(0);
        Field to = game.getField(player.houseFirstIndex);
        Move house = new Move(player, from, to, false, (Card)null);
        house.execute(game);
        Card backCard = new Card(CardType.PLUS_MINUS_4);
        ArrayList<Move> moves = new ArrayList();
        backCard.getMoves(game, fig, moves, player);
        Assertions.assertEquals(0, moves.size());
    }

    @Test
    void onlyOneLegalMoveIfCanGoIntoHouse() {
        String conf = "snnnnknnnnsnnnnknnnn";
        int figureCount = 2;
        int maxMoves = 10000000;
        int handCardCount = 10;
        Game game = new Game(conf, figureCount, handCardCount, maxMoves);
        game.initDeck();
        game.distributeCards();
        Player player = (Player)game.players.get(0);
        Figure fig = (Figure)player.figures.get(0);
        Move start = new Move(player, (Card)null);
        start.execute(game);
        Field from = game.getField(0);
        Field to = game.getField(19);
        Move beforeStart = new Move(player, from, to, false, (Card)null);
        beforeStart.execute(game);
        Card twoCard = new Card(CardType.NORMAL_2);
        ArrayList<Move> moves = new ArrayList();
        twoCard.getMoves(game, fig, moves, player);
        Assertions.assertEquals(1, moves.size());
        Assertions.assertEquals(FieldType.HOUSE, ((Move)moves.get(0)).getFieldTypeTo());
    }

    @Test
    void onlyOneLegalMoveIfCantGoIntoHouse() {
        String conf = "snnnnknnnnsnnnnknnnn";
        int figureCount = 2;
        int maxMoves = 10000000;
        int handCardCount = 10;
        Game game = new Game(conf, figureCount, handCardCount, maxMoves);
        game.initDeck();
        game.distributeCards();
        Player player = (Player)game.players.get(0);
        Figure fig = (Figure)player.figures.get(0);
        Move start = new Move(player, (Card)null);
        start.execute(game);
        Field from = game.getField(0);
        Field to = game.getField(19);
        Move beforeStart = new Move(player, from, to, false, (Card)null);
        beforeStart.execute(game);
        Card tenCard = new Card(CardType.NORMAL_10);
        ArrayList<Move> moves = new ArrayList();
        tenCard.getMoves(game, fig, moves, player);
        Assertions.assertEquals(1, moves.size());
        Assertions.assertEquals(false, FieldType.HOUSE == ((Move)moves.get(0)).getFieldTypeTo());
    }

    @Test
    void cantGoOverFigureInHouse() {
        String conf = "snnnnknnnnsnnnnknnnn";
        int figureCount = 2;
        int maxMoves = 10000000;
        int handCardCount = 10;
        Game game = new Game(conf, figureCount, handCardCount, maxMoves);
        game.initDeck();
        game.distributeCards();
        Player player = (Player)game.players.get(0);
        Figure fig = (Figure)player.figures.get(0);
        Figure fig2 = (Figure)player.figures.get(1);
        Move start = new Move(player, (Card)null);
        start.execute(game);
        Field from = game.getField(0);
        Field to = game.getField(player.houseFirstIndex);
        Move house = new Move(player, from, to, false, (Card)null);
        house.execute(game);
        start.execute(game);
        from = game.getField(0);
        to = game.getField(19);
        Move beforeStart = new Move(player, from, to, false, (Card)null);
        beforeStart.execute(game);
        Card threeCard = new Card(CardType.NORMAL_3);
        ArrayList<Move> moves = new ArrayList();
        threeCard.getMoves(game, fig2, moves, player);
        Assertions.assertEquals(0, moves.size());
    }

    @Test
    void canGoOverEmptyInHouse() {
        String conf = "snnnnknnnnsnnnnknnnn";
        int figureCount = 2;
        int maxMoves = 10000000;
        int handCardCount = 10;
        Game game = new Game(conf, figureCount, handCardCount, maxMoves);
        game.initDeck();
        game.distributeCards();
        Player player = (Player)game.players.get(0);
        Figure fig = (Figure)player.figures.get(0);
        Move start = new Move(player, (Card)null);
        start.execute(game);
        Field from = game.getField(0);
        Field to = game.getField(19);
        Move beforeStart = new Move(player, from, to, false, (Card)null);
        beforeStart.execute(game);
        Card threeCard = new Card(CardType.NORMAL_3);
        ArrayList<Move> moves = new ArrayList();
        threeCard.getMoves(game, fig, moves, player);
        Assertions.assertEquals(1, moves.size());
    }

    @Test
    void cantGoOverFigureOnStartField() {
        String conf = "snsn";
        int figureCount = 2;
        int maxMoves = 10000000;
        int handCardCount = 10;
        Game game = new Game(conf, figureCount, handCardCount, maxMoves);
        game.initDeck();
        game.distributeCards();
        Player player1 = (Player)game.players.get(0);
        Figure fig1 = (Figure)player1.figures.get(0);
        Player player2 = (Player)game.players.get(1);
        Figure fig2 = (Figure)player2.figures.get(0);
        Move start1 = new Move(player1, (Card)null);
        start1.execute(game);
        Move start2 = new Move(player2, (Card)null);
        start2.execute(game);
        Card threeCard = new Card(CardType.NORMAL_3);
        ArrayList<Move> moves = new ArrayList();
        threeCard.getMoves(game, fig1, moves, player1);
        Assertions.assertEquals(0, moves.size());
    }

    @Test
    void canGoOverFigureOnWrongStartField() {
        String conf = "snsn";
        int figureCount = 2;
        int maxMoves = 10000000;
        int handCardCount = 10;
        Game game = new Game(conf, figureCount, handCardCount, maxMoves);
        game.initDeck();
        game.distributeCards();
        Player player1 = (Player)game.players.get(0);
        Figure fig1 = (Figure)player1.figures.get(0);
        Player player2 = (Player)game.players.get(1);
        Figure fig2 = (Figure)player2.figures.get(0);
        Move start1 = new Move(player1, (Card)null);
        start1.execute(game);
        Field from = game.getField(0);
        Field to = game.getField(2);
        Move onStart = new Move(player1, from, to, false, (Card)null);
        onStart.execute(game);
        Card startCard = new Card(CardType.START_13);
        ArrayList<Move> moves = new ArrayList();
        startCard.getMoves(game, fig2, moves, player2);
        Assertions.assertEquals(1, moves.size());
    }
}