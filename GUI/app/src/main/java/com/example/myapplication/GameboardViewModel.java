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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import java.util.List;

public class GameboardViewModel extends ViewModel {

    MutableLiveData<GameInformation> gameInformation = new MutableLiveData<>();
    MutableLiveData<Integer> field_size= new MutableLiveData<>();
    MutableLiveData<Integer> player_count= new MutableLiveData<>();
    MutableLiveData<Integer> figure_count= new MutableLiveData<>();
    //MutableLiveData<Game_board_creator> game_board_creator= new MutableLiveData<>();
    MutableLiveData<Figure_handler> figure_handler= new MutableLiveData<>();
    MutableLiveData<int[]> start_fields = new MutableLiveData<>();
    MutableLiveData<int[]> drawFields = new MutableLiveData<>();

    MutableLiveData<List<Integer>> FiguresInBank = new MutableLiveData<>(new ArrayList<Integer>(6));

    MutableLiveData<List<Integer>> CardsInHand = new MutableLiveData<>(new ArrayList<Integer>(6));
    MutableLiveData<PlayerInformationTable> playerInformationTable = new MutableLiveData<>();

    MutableLiveData<Dictionary<Integer, String>> playerNames = new MutableLiveData<>(new Hashtable<>());
    MutableLiveData<Integer> LastPlayer = new MutableLiveData<>();

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

    public MutableLiveData<int[]> getStart_fields() {
        return start_fields;
    }

    public void setStart_fields(int[] start_fields) {
        this.start_fields.setValue(start_fields);
    }

    public MutableLiveData<int[]> getDrawFields() {
        return drawFields;
    }

    public void setDrawFields(int[] drawFields) {
        this.drawFields.setValue(drawFields);
    }



    public MutableLiveData<GameInformation> getGameInformation() {
       return gameInformation;
    }
    /**
     * Sets the gameInformation
     * @param gameInformation is the gameInformation to be set
     */
    public void setGameInformation(GameInformation gameInformation) {
        Dictionary<Integer, String> namesDict = this.playerNames.getValue();
        for (   Order order: gameInformation.getPlayerOrder().getOrder()) {
                namesDict.put(order.getClientId(), order.getName());
        }
        this.playerNames.setValue(namesDict);
        this.gameInformation.setValue(gameInformation);
    }

    public MutableLiveData<List<Integer>> getFiguresInBank() {
        return FiguresInBank;
    }

    public void setFiguresInBank(List<Integer> figuresInBank) {
        FiguresInBank.setValue(figuresInBank);
    }

    public MutableLiveData<PlayerInformationTable> getPlayerInformationTable() {
        return playerInformationTable;
    }

    public void setPlayerInformationTable(PlayerInformationTable playerInformationTable) {
        this.playerInformationTable.setValue(playerInformationTable);
    }

    public MutableLiveData<Dictionary<Integer, String>> getPlayerNames() {
        return playerNames;
    }
    public void setPlayerNames(Dictionary playerNames) {
        this.playerNames.setValue(playerNames);
    }
    /**
     * Returns the name of the player for the given ClientId
     * @param clientId is the number of the player
     * @return the name of the player
     */
    public String getPlayerName(int clientId) {
        return( playerNames.getValue().get(clientId));
    }

    public MutableLiveData<Integer> getLastPlayer() {
        return LastPlayer;
    }

    public void setLastPlayer(Integer lastPlayer) {
        LastPlayer.setValue(lastPlayer);
    }

    /**
     * Changes the value of figuresInBank for the given amount for the given playerww
     * @param playerNumber the number of the player
     * @param figureNumber the number of figures to change
     */
    public void changeFigureInBankValue(int playerNumber, int figureNumber) {
        List<Integer> temp = this.FiguresInBank.getValue();
        temp.set(playerNumber, temp.get(playerNumber) + figureNumber);
        FiguresInBank.setValue(temp);
    }
}
