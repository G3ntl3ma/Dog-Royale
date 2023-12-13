package com.example.DogRoyalClient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentGameViewModel extends ViewModel {

    MutableLiveData<String> current_game_id = new MutableLiveData<>();

    public MutableLiveData<String> getCurrent_game_id() {
        return current_game_id;
    }

    public void setCurrent_game_id(String game_id) {
        this.current_game_id.setValue(game_id);
    }
}
