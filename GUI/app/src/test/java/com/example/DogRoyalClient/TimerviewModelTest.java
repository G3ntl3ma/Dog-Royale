package com.example.DogRoyalClient;

import static org.junit.jupiter.api.Assertions.*;

import androidx.lifecycle.MutableLiveData;

import org.junit.jupiter.api.Test;

class TimerviewModelTest {
    private TimerviewModel timerviewModel;
    private MutableLiveData<String> time;

    @Test
    void getTime() {
        timerviewModel.setTime(String.valueOf(time));
        assertEquals(time,timerviewModel.getTime());
    }

    @Test
    void setTime() {
        timerviewModel.setTime(String.valueOf(time));
        assertEquals(time,timerviewModel.getTime());
    }
}