package com.example.DogRoyalClient;

import static org.junit.jupiter.api.Assertions.*;

import androidx.lifecycle.MutableLiveData;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class CurrentGameViewModelTest {

    @Mock
    private CurrentGameViewModel currentGameViewModel;

    MutableLiveData<String> current_game_id = new MutableLiveData<>();
    @Test
    void getCurrent_game_id() {
        currentGameViewModel.setCurrent_game_id(String.valueOf(current_game_id));
        assertEquals(current_game_id,currentGameViewModel.getCurrent_game_id());
    }

    @Test
    void setCurrent_game_id() {
        currentGameViewModel.setCurrent_game_id(String.valueOf(current_game_id));
        assertEquals(current_game_id,currentGameViewModel.getCurrent_game_id());
    }
}