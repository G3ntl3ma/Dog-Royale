package com.example.DogRoyalClient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StartScreenViewModel extends ViewModel {
    MutableLiveData<String> username = new MutableLiveData<>();

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }
}
