package com.example.DogRoyalClient;

import static org.junit.jupiter.api.Assertions.*;

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
    void setLayout() {
        gameBoardCreator.setLayout(layout);
        assertEquals(layout,gameBoardCreator.getLayout());
    }

    @Test
    void setWidth() {
        gameBoardCreator.setWidth(width);
        assertEquals(layout,gameBoardCreator.getWidth());
    }

    @Test
    void setPlayer_count() {
        gameBoardCreator.setPlayer_count(player_count);
        assertEquals(player_count,gameBoardCreator.getPlayer_count());
    }

    @Test
    void setField_size() {
        gameBoardCreator.setField_size(field_size);
        assertEquals(field_size,gameBoardCreator.getField_size());
    }

    @Test
    void setFigure_count() {
        gameBoardCreator.setFigure_count(figure_count);
        assertEquals(figure_count,gameBoardCreator.getFigure_count());
    }

    @Test
    void setColors() {
        gameBoardCreator.setColors(colors);
        assertEquals(colors,gameBoardCreator.getColors());
    }

    @Test
    void getLayout() {
        gameBoardCreator.setLayout(layout);
        assertEquals(layout,gameBoardCreator.getLayout());
    }

    @Test
    void getWidth() {
        gameBoardCreator.setWidth(width);
        assertEquals(layout,gameBoardCreator.getWidth());
    }

    @Test
    void getPlayer_count() {
        gameBoardCreator.setPlayer_count(player_count);
        assertEquals(player_count,gameBoardCreator.getPlayer_count());
    }

    @Test
    void getField_size() {
        gameBoardCreator.setField_size(field_size);
        assertEquals(field_size,gameBoardCreator.getField_size());
    }

    @Test
    void getFigure_count() {
        gameBoardCreator.setFigure_count(figure_count);
        assertEquals(figure_count,gameBoardCreator.getFigure_count());
    }

    @Test
    void getColors() {
        gameBoardCreator.setColors(colors);
        assertEquals(colors,gameBoardCreator.getColors());
    }

    @Test
    void getStart_fields() {
        gameBoardCreator.setStart_fields(start_fields);
        assertEquals(start_fields,gameBoardCreator.getStart_fields());
    }

    @Test
    void getCard_draw_fields() {
        gameBoardCreator.setCard_draw_fields(card_draw_fields);
        assertEquals(card_draw_fields,gameBoardCreator.getCard_draw_fields());
    }

    @Test
    void setCard_draw_fields() {
        gameBoardCreator.setCard_draw_fields(card_draw_fields);
        assertEquals(card_draw_fields,gameBoardCreator.getCard_draw_fields());
    }

    @Test
    void setStart_fields() {
        gameBoardCreator.setStart_fields(start_fields);
        assertEquals(start_fields,gameBoardCreator.getStart_fields());
    }

    @Test
    void getField_width() {
        assertEquals(field_width*2,gameBoardCreator.getField_width());
    }

    @Test
    void getHomefield_size() {
        assertEquals(homefield_size,gameBoardCreator.getHomefield_size());
    }

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