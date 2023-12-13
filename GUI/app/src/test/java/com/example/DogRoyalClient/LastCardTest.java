package com.example.DogRoyalClient;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class LastCardTest {

    @Mock
    private LastCard lastCard;
    private CardType type;
    private boolean show ;

    @Test
    public void setNewLastCard() {
        lastCard.setNewLastCard(type);
        Mockito.verify(lastCard).setNewLastCard(type);
    }

    @Test
    public void lastCardAvailable() {
        lastCard.showLastCard(show);
        Mockito.verify(lastCard).showLastCard(show);
    }
}