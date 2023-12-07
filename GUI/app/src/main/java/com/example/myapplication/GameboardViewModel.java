package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameboardViewModel extends ViewModel {

    MutableLiveData<GameInformation> gameInformation = new MutableLiveData<>();
    MutableLiveData<Integer> field_size= new MutableLiveData<>();
    MutableLiveData<Integer> player_count= new MutableLiveData<>();
    MutableLiveData<Integer> figure_count= new MutableLiveData<>();
    //MutableLiveData<Game_board_creator> game_board_creator= new MutableLiveData<>();
    MutableLiveData<Figure_handler> figure_handler= new MutableLiveData<>();

    public MutableLiveData<Integer> getField_size() {
        return field_size;
    }

    public void setField_size(int field_size) {
        this.field_size.setValue(field_size);
    }

    public MutableLiveData<Integer> getPlayer_count() {
        return player_count;
    }

    public void setPlayer_count(int player_count) {
        this.player_count.setValue(player_count);
    }

    public MutableLiveData<Integer> getFigure_count() {
        return figure_count;
    }

    public void setFigure_count(int figure_count) {
        this.figure_count.setValue(figure_count);
    }

    //public MutableLiveData<Game_board_creator> getGame_board_creator() {return game_board_creator;}

    //public void setGame_board_creator(Game_board_creator game_board_creator) {this.game_board_creator.setValue(game_board_creator);}

    public MutableLiveData<Figure_handler> getFigure_handler() {return figure_handler;}

    public void setFigure_handler(Figure_handler figure_handler) {this.figure_handler.setValue(figure_handler);}

    public MutableLiveData<GameInformation> getGameInformation() {
        return gameInformation;
    }

    public void setGameInformation(GameInformation gameInformation) {
        this.gameInformation.setValue(gameInformation);
    }
}
