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
        // 创建一个 Player 实例
        Player player = new Player(1, 4);

        // 添加一些卡片到玩家的卡片列表中
        player.getCardList().add(Card.card2);
        player.getCardList().add(Card.card3);
        player.getCardList().add(Card.card5);

        // 调用 getCardListInteger 方法
        ArrayList<Integer> cardListInteger = player.getCardListInteger();

        // 断言 cardListInteger 是否包含卡片的整数值
        assertNotNull(cardListInteger);
        assertTrue(cardListInteger.contains(Card.card2.getOrdinal()));
        assertTrue(cardListInteger.contains(Card.card3.getOrdinal()));
        assertTrue(cardListInteger.contains(Card.card5.getOrdinal()));
    }
    @Test
    public void testPrintInfo() {
        // 创建一个 Player 实例
        Player player = new Player(1, 4);
        player.setFiguresInBank(2);
        player.setFiguresInHouse(1);
        player.setOutThisRound(true);

        // 重定向System.out以捕获输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // 调用 printInfo 方法
        player.printInfo();

        // 还原System.out
        System.setOut(System.out);

        // 从捕获的输出中检查是否包含期望的信息
        String output = outputStream.toString();
        assertTrue(output.contains("player 1 figBank 2 figs in house 1 out this round true"));
    }
    @Test
    public void testPrintHouse() {
        // 创建一个 Player 实例
        Player player = new Player(1, 4);

        // 创建一些 Figure 并将它们放入 house
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

        // 重定向 System.out 以捕获输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // 调用 printHouse 方法
        player.printHouse();

        // 还原 System.out
        System.setOut(System.out);

        // 从捕获的输出中检查是否包含期望的信息
        String output = outputStream.toString();
        assertTrue(output.contains("house: 1-2-3-"));
    }
    @Test
    void testDraw() {
        // 创建一个模拟的Game对象
        Game game = new Game("conf", 4, 5, 50, 0);

        // 创建一个Player实例
        Player player = new Player(1, 4);

        // 确保玩家的cardList最初是空的
        assertTrue(player.getCardList().isEmpty());

        // 调用draw方法
        player.draw(game);

        // 确保抽取的卡片已添加到玩家的cardList中
        assertFalse(player.getCardList().isEmpty());
        assertEquals(1, player.getCardList().size());

        // 确保抽取的卡片与游戏中的卡片相同
        assertEquals(game.getDrawnCard(), player.getCardList().get(0));

        // 确保游戏的牌堆减少了一张牌
        assertEquals(game.getDeck().size(), 49);
    }
    @Test
    void testPrintCards() {
        // 创建一个Player实例
        Player player = new Player(1, 4);

        // 确保玩家的cardList最初是空的
        assertTrue(player.getCardList().isEmpty());

        // 向玩家的cardList添加一些卡片
        player.getCardList().add(Card.card2);
        player.getCardList().add(Card.card3);
        player.getCardList().add(Card.card5);

        // 使用System.out.captureXXX()捕获标准输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // 调用printCards方法
        player.printCards();

        // 恢复System.out
        System.setOut(System.out);

        // 验证printCards方法是否按预期输出了卡片
        assertEquals("card1 card2 card3 ", outputStream.toString().trim());
    }
    @Test
    void testSetExclude() {
        // 创建一个Player实例
        Player player = new Player(1, 4);

        // 确保玩家最初没有被排除
        assertFalse(player.isExcluded());

        // 调用setExclude方法
        player.setExclude();

        // 验证setExclude方法是否正确将玩家标记为被排除
        assertTrue(player.isExcluded());
    }
    @Test
    void testSetOutThisRound() {
        // 创建一个 Player 实例，可能需要提供必要的参数
        Player player = new Player(1, 4);

        // 调用 setOutThisRound 方法
        player.setOutThisRound();

        // 验证 outThisRound 是否被正确设置为 true
        assertTrue(player.isOutThisRound());
    }
    @Test
    void testGenerateMoves() {
        // 创建一个 Game 实例，可能需要提供必要的参数
        Game game = new Game("conf", 4, 5, 50, 0);

        // 创建一个 Player 实例，可能需要提供必要的参数
        Player player = new Player(1, 4);

        // 添加一些手牌到玩家
        player.getCardList().add(Card.card5);
        player.getCardList().add(Card.card2);

        // 调用 generateMoves 方法
        ArrayList<Move> moves = player.generateMoves(game);

        // 验证生成的移动列表是否不为null且不为空
        assertNotNull(moves);
        assertFalse(moves.isEmpty());
    }
}
