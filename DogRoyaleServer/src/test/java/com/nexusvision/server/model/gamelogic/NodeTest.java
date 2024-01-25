package com.nexusvision.server.model.gamelogic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeTest {

    private Node testNode;
    private Move mockMove;
    private Game mockGame;

    @Before
    public void setUp() {
        mockMove = mock(Move.class);
        mockGame = mock(Game.class);
        testNode = new Node(mockMove, null);
    }

    @Test
    public void testNodeConstructor() {
        assertNotNull(testNode);
        assertEquals(0, testNode.getVisits());
        assertEquals(0, testNode.getValue());
        assertFalse(testNode.getHaschildren());
    }

    @Test
    public void testSetHashWhenNull() {
        testNode.setHash(123);
        assertEquals((Integer) 123, testNode.getHashcode());
    }

    @Test
    public void testIncVisits() {
        testNode.incVisits();
        assertEquals(1, testNode.getVisits());
    }

    @Test
    public void testAddValue() {
        testNode.addValue(5);
        assertEquals(5, testNode.getValue());
    }

    @Test
    public void testGetUtc() {
        // set father node
        Node parentNode = new Node(mock(Move.class), null);
        parentNode.incVisits(); // assume father node has get once call
        parentNode.addValue(10); // assume value

        Node childNode = new Node(mock(Move.class), parentNode);
        childNode.incVisits(); // assume child node has get once call
        childNode.addValue(5); // assume value

        // assert
        float expectedUtc = 5;
        assertEquals(expectedUtc, childNode.getutc(), 0.001);
    }
}

