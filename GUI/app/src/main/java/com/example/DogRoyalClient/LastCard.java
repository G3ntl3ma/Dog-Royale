/**
 * @author: leisen
 */
package com.example.DogRoyalClient;

import com.example.DogRoyalClient.databinding.FragmentGameBoardBinding;

enum CardType {
    MAGNET, //0
    COPY, //1
    NORMAL_2,
    NORMAL_3,
    PLUS_MINUS_4,
    NORMAL_5,
    NORMAL_6,
    RANGE_7,
    NORMAL_8,
    NORMAL_9,
    NORMAL_10,
    START_1_11,
    NORMAL_12,
    START_13,
    SWAP,
}

public class LastCard {
    private LastCardViewModel viewModel;
    public LastCard() {
        this.viewModel = MainActivity.getLastCardViewModel();
    }

    /** Setze eine neue Karte als letzt Karte ein
     *
     * @param type muss von dem Enum Cardtype sein
     */
    public void setNewLastCard(CardType type){
        viewModel.setLastCard(type);
    }

    public void showLastCard(boolean show){
        viewModel.setShowLastCard(show);
    }

}
