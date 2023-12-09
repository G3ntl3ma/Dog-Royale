package com.example.myapplication;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

class LastCardTest {

    @Mock
    private LastCard lastCard;
    private CardType type;

    @Test
    public void setNewLastCard() {
        lastCard.setNewLastCard(type);
        assertEquals(type,lastCard.gettype());
    }

    @Test
    public void lastCardAvailable() {
        Mockito.doReturn("true").when(lastCard.lastCardAvailable());
        Assertions.assertEquals("true", lastCard.lastCardAvailable());
    }
}