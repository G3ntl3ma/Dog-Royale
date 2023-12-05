package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameboardViewModel extends ViewModel {
    MutableLiveData<Integer> field_size= new MutableLiveData<>();
    MutableLiveData<Integer> player_count= new MutableLiveData<>();
    MutableLiveData<Integer> figure_count= new MutableLiveData<>();

    public MutableLiveData<Integer> getField_size() {
        return field_size;
    }

    public void setField_size(int field_size) {
        this.field_size.setValue(field_size);
    }

    public MutableLiveData<Integer> getPlayer_count() {
        return player_count;
    }

    public void setPlayer_count(int player_count) {
        this.player_count.setValue(player_count);
    }

    public MutableLiveData<Integer> getFigure_count() {
        return figure_count;
    }

    public void setFigure_count(int figure_count) {
        this.figure_count.setValue(figure_count);
    }
}
