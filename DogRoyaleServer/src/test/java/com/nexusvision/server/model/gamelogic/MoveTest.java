package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    @Test
    void getSelectedValue_shouldCalculateCorrectly() {
        // arrange
        Field startField = new Field(1,FieldType.START);
        Field houseField = new Field(4,FieldType.HOUSE);
        Player player = new Player(1,4);
        Card card = Card.card2;

        //build Move object
        Move move = new Move(player, startField, houseField, false, card);
        int selectedValue = move.getSelectedValue();

        // assert
        assertEquals(3, selectedValue);
    }

    @Test
    public void testEqual() {
        //arrange
        Player player = new Player(1,2);
        Field fromField = new Field(1,FieldType.HOUSE);
        Field toField = new Field(2,FieldType.START);
        Card cardUsed = Card.card2;
        // set new 2 move
        Move move1 = new Move(player, fromField, toField, false, cardUsed);
        Move move2 = new Move(player, fromField, toField, false, cardUsed);

        // test equal
        boolean result = move1.equal(move2);

        // assert
        assertTrue(result);
    }

    @Test
    public void testExecute() {
        // arrange
        Game game = new Game("conf", 4, 5, 50, 0);

        // build state
        Player player = new Player(1,4);
        Field fromField = new Field(1,FieldType.HOUSE);
        Field toField = new Field(2,FieldType.START);
        Card cardUsed = Card.card2;

        // build move object
        Move move = new Move(player, fromField, toField, false, cardUsed);

        // call execute
        UndoMove undoMove = move.execute(game);

        // assert
        assertNotNull(undoMove);
    }

    @Test
    public void testPrintMove() {
        // 创建玩家、字段和卡片
        Player player = new Player(1, 4);
        Field fromField = new Field(1, FieldType.HOUSE);
        Field toField = new Field(2, FieldType.START);
        Card cardUsed = Card.card2; // 根据实际枚举值来引用

        // 创建移动对象
        Move move = new Move(player, fromField, toField, false, cardUsed);

        // 使用Mockito的PrintStream进行捕获System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // 调用printMove方法
        move.printMove();

        // 恢复System.out
        System.setOut(System.out);

        // 验证输出是否符合预期
        String expectedOutput = "card type card2 from 1 to 2 swap figs false isStartMove false player.color 1";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testGetFieldTypeTo() {
        // arrange
        Player player = new Player(1, 4);
        Field toField = new Field(1, FieldType.HOUSE);

        // build move object
        Move move = new Move(player, null, toField, false, null);

        // call getFieldTypeto
        FieldType fieldType = move.getFieldTypeTo();

        // assert
        assertNotNull(fieldType);
    }
}
