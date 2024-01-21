package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.nexusvision.server.model.messages.game.TypeGame.move;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    public void testSetHash() {
        Move move = new Move(new Player(1, 4), new Field(1, FieldType.START), new Field(2, FieldType.HOUSE), false, Card.card2);
        Node node = new Node(move, null);

        // Test setting hashcode for the first time
        int hash1 = 12345;
        node.setHash(hash1);
        assertEquals(hash1, (int)node.getHashcode());

        // Test setting hashcode again with the same value
        node.setHash(hash1);
        assertEquals(hash1, (int)node.getHashcode());

        // Test setting hashcode again with a different value (should exit with an error)
        int hash2 = 54321;
        assertThrows(RuntimeException.class, () -> {
            node.setHash(hash2);
        });
    }
    @Test
    void testExpand() {
        // Arrange
        Game game = new Game("conf", 4, 5, 50, 0);
        Node rootNode = new Node(null, null);

        // Ensure haschildren is initially false
        assertFalse(rootNode.getHaschildren(), "haschildren should be false initially");

        // Act
        rootNode.expand(game);

        // Assert
        assertTrue(rootNode.getHaschildren(), "haschildren should be true after expansion");
        assertNotNull(rootNode.getChildren(), "Children should not be null after expansion");
        assertTrue(rootNode.getChildren().size() > 0, "There should be at least one child node");

        for (Node child : rootNode.getChildren()) {
            assertNotNull(child.getMove(), "Child's move should not be null");
        }
    }

    @Test
    public void testVisitsAndValue() {
        Move move = new Move(new Player(1, 4), new Field(1, FieldType.START), new Field(2, FieldType.HOUSE), false, Card.card2);
        Node node = new Node(move, null);

        assertEquals(0, node.getVisits());
        assertEquals(0, node.getValue());

        // Test incrementing visits and adding value
        node.incVisits();
        node.addValue(10);

        assertEquals(1, node.getVisits());
        assertEquals(10, node.getValue());
    }

    @Test
    public void testGetutc() {
        // build a rootNode object
        Move move = new Move(new Player(1, 4), new Field(1, FieldType.START), new Field(2, FieldType.HOUSE), false, Card.card2);
        Node node = new Node(move, null);
        Node rootNode = new Node(move,node);

        //do some operation to make sure that, rootNode have value.
        Game game = new Game("conf", 4, 5, 50, 0);
        rootNode.expand(game);
        // get childNode list
        ArrayList<Node> children = rootNode.getChildren();


        children.get(0).incVisits();
        children.get(0).addValue(5);

        // call getutc
        float result = rootNode.getutc();

        //assert the result should not smaller than 0
        assertTrue(result >= 0);
    }
}
