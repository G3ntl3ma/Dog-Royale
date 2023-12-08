package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LastCardViewModel extends ViewModel {
    MutableLiveData<CardType> lastCard = new MutableLiveData<>();
    MutableLiveData<Boolean> showLastCard = new MutableLiveData<>();

    public MutableLiveData<CardType> getLastCard() {
        return lastCard;
    }

    public void setLastCard(CardType lastCard) {
        this.lastCard.setValue(lastCard);
    }

    public MutableLiveData<Boolean> getShowLastCard() {
        return showLastCard;
    }

    public void setShowLastCard(boolean showLastCard) {
        this.showLastCard.setValue(showLastCard);
    }
}
