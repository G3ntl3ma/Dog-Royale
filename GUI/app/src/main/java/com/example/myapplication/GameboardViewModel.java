package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.GameInformationClasses.Color;
import com.example.myapplication.GameInformationClasses.DrawCardFields;
import com.example.myapplication.GameInformationClasses.Observer;
import com.example.myapplication.GameInformationClasses.Order;
import com.example.myapplication.GameInformationClasses.OrderType;
import com.example.myapplication.GameInformationClasses.PlayerOrder;
import com.example.myapplication.GameInformationClasses.StartFields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        //testweise
       gameInformation.setValue(new GameInformation(new Integer(5), new Integer(22), new Integer(5) , Arrays.asList(new Color(1, R.color.p1_color), new Color(2, R.color.p2_color), new Color(3, R.color.p3_color), new Color(4, R.color.p4_color), new Color(5, R.color.p5_color), new Color(6, R.color.p6_color)), new DrawCardFields(5, Arrays.asList(1, 4, 7, 10, 13)), new StartFields(5, Arrays.asList(2, 5, 7, 10, 14)), new Integer(5), new PlayerOrder(OrderType.fixed, Arrays.asList(new Order(1, "OwO"), new Order(2, "UwU"), new Order(3, "AwA"), new Order(4, "QwQ"), new Order(5, "XwX"))),  Arrays.asList(new Observer(new Integer(1), "OwO")), new Integer(5), new Integer(5), new Integer(1), new Integer(15), new Integer(5)));
        return gameInformation;
    }

    public void setGameInformation(GameInformation gameInformation) {
        this.gameInformation.setValue(gameInformation);
    }
}
