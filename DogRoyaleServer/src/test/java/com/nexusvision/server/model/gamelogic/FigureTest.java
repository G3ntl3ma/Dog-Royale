package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.FieldType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class FigureTest {

    private Figure figure;
    private Field mockField;

    @Before
    public void setUp() {
        mockField = mock(Field.class);
        figure = new Figure(1, 1); // 使用默认构造函数
    }

    @Test
    public void testFigureConstructor() {
        assertEquals(1, figure.getOwnerId());
        assertEquals(1, figure.getFigureId());
        assertTrue(figure.isOnBench());
        assertFalse(figure.isInHouse());
        assertNull(figure.getField());
    }
    @Test
    public void testSetAndGetField() {
        figure.setField(mockField);
        assertEquals(mockField, figure.getField());
    }
    @Test
    public void testSetAndGetIsOnBench() {
        figure.setOnBench(false);
        assertFalse(figure.isOnBench());
    }

    @Test
    public void testSetAndGetIsInHouse() {
        figure.setInHouse(true);
        assertTrue(figure.isInHouse());
    }
    @Test
    public void testPrint() {
        figure.print(); //is just test print mehtod is still work good
    @org.junit.jupiter.api.Test
    public void testHash() {
        // Arrange
        Field field1 = new Field(1, FieldType.NORMAL);
        Field field2 = new Field(2, FieldType.HOUSE);
        Field field3 = new Field(3, FieldType.DRAW);

        Figure figure1 = new Figure(field1, true, false, 1);
        Figure figure2 = new Figure(field1, true, false, 1);
        Figure figure3 = new Figure(field2, false, true, 2);
        Figure figure4 = new Figure(field3, false, false, 3);

        // Act
        int hash1 = figure1.hash();
        int hash2 = figure2.hash();
        int hash3 = figure3.hash();
        int hash4 = figure4.hash();

        // Assert
        assertEquals(hash1, hash2); // Same figures should have the same hash
        assertNotEquals(hash1, hash3); // Different field should have different hash
        assertNotEquals(hash1, hash4); // Different isOnBench should have different hash

    }
}
