package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentGameViewModel extends ViewModel {

    MutableLiveData<Integer> current_game_id = new MutableLiveData<>(); //changed

    public MutableLiveData<Integer> getCurrent_game_id() {
        return current_game_id;
    } //changed

    public void setCurrent_game_id(int game_id) { //changed
        this.current_game_id.setValue(game_id);
    }
}
