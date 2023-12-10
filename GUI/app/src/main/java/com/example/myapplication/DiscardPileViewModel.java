package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.messages.game.AbstractGameMessage;
import com.example.myapplication.messages.game.BoardState;

import java.util.ArrayList;
import java.util.List;

public class DiscardPileViewModel extends ViewModel {
    private String output = "";
    MutableLiveData<String> OutputString = new MutableLiveData<>();

    public void set_DiscardPile(List<BoardState.DiscardItem> items){
        System.out.println("Set DiscardPile has been reached!");
        GameboardViewModel viewModel = MainActivity.getGameboardViewModel();
        output= "";
        for(int i = 0; i < items.size(); i++){
            output += viewModel.getPlayerName(items.get(i).getClientId()) + ": " + items.get(i).getCard() + "\n";
        }
        System.out.println("The Output String is: " + output);
        this.OutputString.setValue(output);
    }
    public MutableLiveData<String> getOutputString(){
        System.out.println(OutputString.getValue());
        return OutputString;
    }
}
