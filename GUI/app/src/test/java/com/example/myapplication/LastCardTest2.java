package com.example.myapplication;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

public class LastCardTest2 {

    private LastCard lastCard;
    private LastCardViewModel mockViewModel;

    @Before
    public void setUp() {
        // Create a mock ViewModel
        mockViewModel = mock(LastCardViewModel.class);

        // Assuming MainActivity or the place where you get ViewModel can be mocked or altered
        // to return the mock ViewModel
        when(MainActivity.getLastCardViewModel()).thenReturn(mockViewModel);

        // Now when LastCard is initialized, it should get the mock ViewModel
        lastCard = new LastCard();
    }

    @Test
    public void testSetNewLastCard() {
        // Act
        lastCard.setNewLastCard(CardType.MAGNET);

        // Assert
        verify(mockViewModel).setLastCard(CardType.MAGNET);
    }

    @Test
    public void testShowLastCard() {
        // Act
        lastCard.showLastCard(true);

        // Assert
        verify(mockViewModel).setShowLastCard(true);
    }
}