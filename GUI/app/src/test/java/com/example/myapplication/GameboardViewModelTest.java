package com.example.myapplication;

import static org.junit.jupiter.api.Assertions.*;

import androidx.lifecycle.MutableLiveData;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class GameboardViewModelTest {
    @Mock
    private GameboardViewModel gameboardViewModel;
    private MutableLiveData<GameInformation> gameInformation;
    private MutableLiveData<Integer> field_size;
    private MutableLiveData<Integer> player_count;
    private MutableLiveData<Integer> figure_count;
    private MutableLiveData<Figure_handler> figure_handler;
    private MutableLiveData<int[]> start_fields;
    private MutableLiveData<int[]> drawFields;


    @Test
    void getField_size() {
        gameboardViewModel.setField_size(field_size.getValue());
        assertEquals(field_size.getValue(),gameboardViewModel.getField_size());
    }

    @Test
    void setField_size() {
        gameboardViewModel.setField_size(field_size.getValue());
        assertEquals(field_size.getValue(),gameboardViewModel.getField_size());
    }

    @Test
    void getPlayer_count() {
        gameboardViewModel.setPlayer_count(player_count.getValue());
        assertEquals(player_count.getValue(),gameboardViewModel.getPlayer_count());
    }

    @Test
    void setPlayer_count() {
        gameboardViewModel.setPlayer_count(player_count.getValue());
        assertEquals(player_count.getValue(),gameboardViewModel.getPlayer_count());
    }

    @Test
    void getFigure_count() {
        gameboardViewModel.setFigure_count(figure_count.getValue());
        assertEquals(figure_count.getValue(),gameboardViewModel.getPlayer_count());
    }

    @Test
    void setFigure_count() {
        gameboardViewModel.setFigure_count(figure_count.getValue());
        assertEquals(figure_count.getValue(),gameboardViewModel.getPlayer_count());
    }

    @Test
    void getFigure_handler() {
        gameboardViewModel.setFigure_handler(figure_handler.getValue());
        assertEquals(figure_handler.getValue(),gameboardViewModel.getFigure_handler());
    }

    @Test
    void setFigure_handler() {
        gameboardViewModel.setFigure_handler(figure_handler.getValue());
        assertEquals(figure_handler.getValue(),gameboardViewModel.getFigure_handler());
    }

    @Test
    void getGameInformation() {
        gameboardViewModel.setGameInformation(gameInformation.getValue());
        assertEquals(gameInformation.getValue(),gameboardViewModel.getGameInformation());
    }

    @Test
    void setGameInformation() {
        gameboardViewModel.setGameInformation(gameInformation.getValue());
        assertEquals(gameInformation.getValue(),gameboardViewModel.getGameInformation());
    }
}