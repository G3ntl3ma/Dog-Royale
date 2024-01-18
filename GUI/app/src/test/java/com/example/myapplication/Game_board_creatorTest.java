package com.example.myapplication;

import static org.junit.jupiter.api.Assertions.*;

import android.icu.text.ListFormatter;
import android.widget.RelativeLayout;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

class Game_board_creatorTest {
    @Mock
    private Game_board_creator gameBoardCreator;
    private RelativeLayout layout;
    private int width;

    private int player_count;

    private int field_size;

    private int figure_count;

    private List<Integer> colors;

    private List<Integer> start_fields;

    private Tuple[] start_fields_position;

    private List<Integer> card_draw_fields;

    private int field_width;

    private int homefield_size;

    private Tuple home;
    private Tuple vek;

    private int n;
    private int i;

    private double offset;

    @Test
    void createFields() {
        gameBoardCreator.createFields();
        Mockito.verify(gameBoardCreator).createFields();
    }

    @Test
    void createHomeFields() {
        gameBoardCreator.createHomeFields();
        Mockito.verify(gameBoardCreator).createHomeFields();
    }

    @Test
    void fh() {
        double home_x = home.getX() ;
        double home_y = home.getY() ;
        double vek_x = vek.getX();
        double vek_y = vek.getY();
        assertEquals(home_x + 1/offset*vek_x + vek_x/n * i,gameBoardCreator.fh(vek,home,i,n).getX());
        assertEquals(home_y + 1/offset*vek_y + vek_y/n * i,gameBoardCreator.fh(vek,home,i,n).getY());
    }

    @Test
    void createCardDrawfields() {
        gameBoardCreator.createCardDrawfields();
        Mockito.verify(gameBoardCreator).createCardDrawfields();
    }
}