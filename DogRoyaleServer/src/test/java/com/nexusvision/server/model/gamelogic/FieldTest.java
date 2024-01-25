package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.FieldType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FieldTest {

    private Field field;
    private Figure mockFigure;
    private Field mockNextField;
    private Field mockPrevField;

    @Before
    public void setUp() {
        field = new Field(1, 'n');
        mockFigure = mock(Figure.class);
        mockNextField = mock(Field.class);
        mockPrevField = mock(Field.class);
    }

    @Test
    public void testFieldConstructor() {
        assertEquals(1, field.getFieldId());
        assertEquals(FieldType.NORMAL, field.getType());
        assertNull(field.getFigure());
    }
    @Test
    public void testIsEmpty() {
        assertTrue(field.isEmpty());
        field.setFigure(mockFigure);
        assertFalse(field.isEmpty());
    }
    @Test
    public void testSetFigureAndSetEmpty() {
        field.setFigure(mockFigure);
        assertEquals(mockFigure, field.getFigure());

        field.setEmpty();
        assertNull(field.getFigure());
    }
    @Test
    public void testSetNextAndSetHouse() {
        field.setNext(mockNextField);
        assertEquals(mockNextField, field.getNext());

        field.setHouse(mockPrevField);
        assertEquals(mockPrevField, field.getHouse());
    }
    @Test
    public void testHash() {
        int initialHash = field.hash();

        field.setFigure(mockFigure);
        int newHash = field.hash();

        assertNotEquals(initialHash, newHash);
    }
    @Test
    public void testPrintMethods() {
        field.setNext(mockNextField);
        field.setPrev(mockPrevField);

        field.printVal();       // assert there are no excpetion here
        field.printVals();
        field.printType();
        field.printTypes();
    }
    @Test
    public void testSetNextAndSetPrev() {
        field.setNext(mockNextField);
        assertEquals(mockNextField, field.getNext());

        field.setPrev(mockPrevField);
        assertEquals(mockPrevField, field.getPrev());
    }
    @Test
    public void testFieldConstructorWithTypeChar() {
        Field houseField = new Field(2, 'h');
        assertEquals(FieldType.HOUSE, houseField.getType());

        Field drawField = new Field(3, 'k');
        assertEquals(FieldType.DRAW, drawField.getType());

        Field startField = new Field(4, 's');
        assertEquals(FieldType.START, startField.getType());

        Field normalField = new Field(5, 'n');
        assertEquals(FieldType.NORMAL, normalField.getType());

        // test default Field
        Field defaultField = new Field(6, 'x');
        assertEquals(FieldType.NORMAL, defaultField.getType());
    }
}
