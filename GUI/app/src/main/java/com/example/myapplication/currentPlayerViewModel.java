package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.messages.game.AbstractGameMessage;

import java.util.List;

/**
 * This ViewModel contains all Information to Play the Game
 */
public class currentPlayerViewModel extends ViewModel {

    //True, when its the Players Turn, False when not
    MutableLiveData<Boolean> isTurn = new MutableLiveData<>();

    //List of Cards on the Players Hand
    MutableLiveData<List<AbstractGameMessage.Card>> playCards = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsTurn() {
        return isTurn;
    }

    public void setIsTurn(boolean isTurn) {
        this.isTurn.setValue(isTurn);
    }

    public MutableLiveData<List<AbstractGameMessage.Card>> getPlayCard() {
        return playCards;
    }

    public void setPlayCard(List<AbstractGameMessage.Card> playCards) {
        this.playCards.setValue(playCards);
    }
}
