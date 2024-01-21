package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.FieldType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    @Test
    public void testHash() {
        // Arrange
        Field field1 = new Field(1, FieldType.NORMAL);
        Field field2 = new Field(1, FieldType.NORMAL);
        Field field3 = new Field(2, FieldType.NORMAL);
        Field field4 = new Field(1, FieldType.HOUSE);
        Field field5 = new Field(1, FieldType.NORMAL);
        field5.setFigure(new Figure(1,2)); // Assuming Figure class has a constructor that takes a figureId

        // Act
        int hash1 = field1.hash();
        int hash2 = field2.hash();
        int hash3 = field3.hash();
        int hash4 = field4.hash();
        int hash5 = field5.hash();

        // Assert
        assertEquals(hash1, hash2); // Same fields should have the same hash
        assertNotEquals(hash1, hash3); // Different fieldId should have different hash
        assertNotEquals(hash1, hash4); // Different FieldType should have different hash
        assertNotEquals(hash1, hash5); // Different Figure should have different hash

    }
}