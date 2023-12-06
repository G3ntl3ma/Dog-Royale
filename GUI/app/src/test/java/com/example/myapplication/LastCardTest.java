package com.example.myapplication;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

class LastCardTest {

    CardType cardType = new CardType();
    @Mock
    private LastCard lastCard;
    @Test
    void setNewLastCard() {
        Mockito.doReturn("true").when(lastCard.setNewLastCard();
        Assertions.assertEquals("true",lastCard.setNewLastCard());
    }

    @Test
    void lastCardAvailable() {
        Mockito.doReturn("true").when(lastCard.lastCardAvailable());
        Assertions.assertEquals("true", lastCard.lastCardAvailable());
    }
}