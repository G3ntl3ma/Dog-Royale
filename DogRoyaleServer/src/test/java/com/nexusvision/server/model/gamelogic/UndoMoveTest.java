package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


    public class UndoMoveTest {

        @Test
        public void testExecute() {
            // 创建模拟的游戏对象和相关的实例
            Game game = new Game("conf", 4, 5, 50, 0);

            // 创建两个玩家，分别对应移动中的玩家和对手
            Player player = new Player(1, 4);
            Player opponent = new Player(2, 4);

            // 创建两个场地，分别对应移动中的起始和目标场地
            Field startField = new Field(1, FieldType.START);
            Field houseField = new Field(4, FieldType.HOUSE);

            // 创建一张卡片，对应移动中使用的卡片
            Card card = Card.card2;

            // 设置移动的相关实例
            Move move = new Move(player, startField, houseField, false, card);

            // 执行 Move，并得到 UndoMove 对象
            UndoMove undoMove = move.execute(game);

            // 在测试 UndoMove 的 execute 方法之前记录游戏状态
            int playerFiguresInBankBefore = player.getFiguresInBank();
            int opponentFiguresInBankBefore = opponent.getFiguresInBank();
            int playerFiguresInHouseBefore = player.getFiguresInHouse();

            // 执行 UndoMove 的 execute 方法
            undoMove.execute(game);

            // 验证游戏状态是否恢复到执行 Move 之前的状态
            assertEquals(playerFiguresInBankBefore, player.getFiguresInBank());
            assertEquals(opponentFiguresInBankBefore, opponent.getFiguresInBank());
            assertEquals(playerFiguresInHouseBefore, player.getFiguresInHouse());

            // 验证卡片是否正确放回到玩家手中
            assertTrue(player.getCardList().contains(card));
            assertFalse(game.getPile().contains(card));

            // 验证场地状态是否正确
            assertEquals(startField.getFigure(), player.getStartField().getFigure());
        }
}
