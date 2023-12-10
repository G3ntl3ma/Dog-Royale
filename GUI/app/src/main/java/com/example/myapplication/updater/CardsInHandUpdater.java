package com.example.myapplication.updater;

import com.example.myapplication.GameboardViewModel;
import com.example.myapplication.MainActivity;
import com.example.myapplication.messages.game.UpdateDrawCards;

import lombok.Data;

@Data
public class CardsInHandUpdater {

    private GameboardViewModel gameboardViewModel;

    public CardsInHandUpdater() {
        this.gameboardViewModel = MainActivity.getGameboardViewModel();
    }

    /**
     * Updates the cards in hand
     * @param message is the message from which we get the information
     */
    public void update(UpdateDrawCards message) {
        for (UpdateDrawCards.HandCard handCard : message.getHandCards()) {
            gameboardViewModel.getPlayerInformationTable().getValue().changeCardInfo(handCard.getClientId(), handCard.getCount());
        }

    }

}
