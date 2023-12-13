package com.example.DogRoyalClient;

import static org.junit.jupiter.api.Assertions.*;

import android.widget.RelativeLayout;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

class Figure_handlerTest {
    @Mock
    private Figure_handler figure_handler;
    private RelativeLayout relativeLayout;
    private int figure_count;
    private int player_count;
    private List<Integer> colors;
    private int figur_size;

    @Test
    void setLayout() {
        figure_handler.setLayout(relativeLayout);
        assertEquals(relativeLayout,figure_handler.getLayout());
    }

    @Test
    void getLayout() {
        figure_handler.setLayout(relativeLayout);
        assertEquals(relativeLayout,figure_handler.getLayout());
    }

    @Test
    void setFigure_count() {
        figure_handler.setFigure_count(figure_count);
        assertEquals(figure_count,figure_handler.getFigure_count());
    }

    @Test
    void getFigure_count() {
        figure_handler.setFigure_count(figure_count);
        assertEquals(figure_count,figure_handler.getFigure_count());
    }

    @Test
    void setPlayer_count() {
        figure_handler.setPlayer_count(player_count);
        assertEquals(player_count,figure_handler.getPlayer_count());

    }

    @Test
    void getPlayer_count() {
        figure_handler.setPlayer_count(player_count);
        assertEquals(player_count,figure_handler.getPlayer_count());
    }

    @Test
    void setColors() {
        figure_handler.setColors(colors);
        Mockito.verify(figure_handler).setColors(colors);
    }

    @Test
    void setFigure_size() {
        figure_handler.setFigure_size(figur_size);
        assertEquals(figur_size,figure_handler.getFigure_size());
    }

    @Test
    void getFigure_size() {
        figure_handler.setFigure_size(figur_size);
        assertEquals(figur_size,figure_handler.getFigure_size());
    }

    @Test
    void create_figures() {
        figure_handler.create_figures();
        Mockito.verify(figure_handler).create_figures();
    }

    @Test
    void moveFigure() {

    }
}