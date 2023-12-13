package com.example.DogRoyalClient;

import static org.junit.jupiter.api.Assertions.*;

import androidx.lifecycle.MutableLiveData;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class LastCardViewModelTest {
    @Mock
    private LastCardViewModel lastCardViewModel;
    private MutableLiveData<Boolean> show_LastCard;
    private MutableLiveData<CardType> last_Card;

    @Test
    public void getLastCard() {
        lastCardViewModel.setLastCard(last_Card.getValue());
        assertEquals(last_Card,lastCardViewModel.getLastCard());

    }

    @Test
    public void setLastCard() {
        lastCardViewModel.setLastCard(last_Card.getValue());
        assertEquals(last_Card,lastCardViewModel.getLastCard());
    }

    @Test
    public void getShowLastCard() {
        lastCardViewModel.setShowLastCard(show_LastCard.getValue());
        assertEquals(show_LastCard,lastCardViewModel.getShowLastCard());
    }

    @Test
    public void setShowLastCard() {
        lastCardViewModel.setShowLastCard(show_LastCard.getValue());
        assertEquals(show_LastCard,lastCardViewModel.getShowLastCard());
    }
}