package com.example.myapplication;

import static org.junit.jupiter.api.Assertions.*;

import androidx.lifecycle.MutableLiveData;

import org.junit.jupiter.api.Test;

class StartScreenViewModelTest {
    private StartScreenViewModel screenViewModel;
    private MutableLiveData<String> username;
    @Test
    void getUsername() {
        screenViewModel.setUsername(String.valueOf(username));
        assertEquals(String.valueOf(username),screenViewModel.getUsername());
    }

    @Test
    void setUsername() {
        screenViewModel.setUsername(String.valueOf(username));
        assertEquals(String.valueOf(username),screenViewModel.getUsername());
    }
}