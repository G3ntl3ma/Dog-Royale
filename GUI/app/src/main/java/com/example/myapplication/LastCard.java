/**
 * @author: leisen
 */
package com.example.myapplication;

import android.view.View;

import com.example.myapplication.databinding.FragmentGameBoardBinding;

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
    private FragmentGameBoardBinding binding;

    private CardType last_card_name;

    private boolean last_card_played = false;
    public LastCard(FragmentGameBoardBinding binding) {
        this.binding = binding;
        this.binding.lastCardView.setVisibility(View.INVISIBLE);
    }

    /** Setze eine neue Karte als letzt Karte ein
     *
     * @param type muss von dem Enum Cardtype sein
     */
    public void setNewLastCard(CardType type){
        last_card_name = type;
        updateImage();
    }

    /** Wenn eine letzte Karte vorhanden ist, muss dies hier umgestellt werden, damit die Karte angezeigt wird
     *
     * @param card_available boolean
     */
    public void lastCardAvailable(boolean card_available){
        last_card_played = card_available;
        showCard();
    }

    /**
     *
     * @return Die aktuelle letze Karte, als ENUM
     */
    public CardType getLast_card_name() {
        return last_card_name;
    }

    /**
     * Update the last card with the current Card
     */
    private void updateImage(){
        switch (last_card_name) {
            case COPY: binding.lastCardView.setImageResource(R.drawable.card_copy);
                       break;
            case SWAP: binding.lastCardView.setImageResource(R.drawable.card_switch);
                       break;
            case MAGNET: binding.lastCardView.setImageResource(R.drawable.card_magnet);
                       break;
            case RANGE_7: binding.lastCardView.setImageResource(R.drawable.card1to7);
                       break;
            case NORMAL_2: binding.lastCardView.setImageResource(R.drawable.card2);
                       break;
            case NORMAL_3: binding.lastCardView.setImageResource(R.drawable.card3);
                       break;
            case NORMAL_5: binding.lastCardView.setImageResource(R.drawable.card5);
                       break;
            case NORMAL_6: binding.lastCardView.setImageResource(R.drawable.card6);
                       break;
            case NORMAL_8: binding.lastCardView.setImageResource(R.drawable.card8);
                       break;
            case NORMAL_9: binding.lastCardView.setImageResource(R.drawable.card9);
                       break;
            case NORMAL_10: binding.lastCardView.setImageResource(R.drawable.card10);
                       break;
            case NORMAL_12: binding.lastCardView.setImageResource(R.drawable.card12);
                       break;
            case PLUS_MINUS_4: binding.lastCardView.setImageResource(R.drawable.card_plusminus4);
                       break;
            case START_13: binding.lastCardView.setImageResource(R.drawable.card_1_or_11);
                       break;
            case START_1_11: binding.lastCardView.setImageResource(R.drawable.card_start_13);
                       break;
        }
    }

    /**
     * Die letzt Karte wird sichtbar oder unsichtbar gemacht
     */
    private void showCard(){
        if(last_card_played){
            binding.lastCardView.setVisibility(View.VISIBLE);
        }
        else {
            binding.lastCardView.setVisibility(View.INVISIBLE);
        }
    }
}
