package com.example.DogRoyalClient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerviewModel extends ViewModel {
    MutableLiveData<String> time = new MutableLiveData<>();

    public MutableLiveData<String> getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time.setValue(time);
    }
}
