package com.example.DogRoyalClient;

import static org.junit.jupiter.api.Assertions.*;

import android.widget.RelativeLayout;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class FigureTest {
    @Mock
    private Figure figure;
    private RelativeLayout relativeLayout;
    private int color;

    @Test
    void getId() {
        figure.setId(1);
        assertEquals(1,figure.getId());
    }

    @Test
    void setId() {
        figure.setId(1);
        assertEquals(1,figure.getId());
    }

    @Test
    void getPlayernumber() {
        figure.setPlayernumber(1);
        assertEquals(1,figure.getPlayernumber());
    }

    @Test
    void setPlayernumber() {
        figure.setPlayernumber(1);
        assertEquals(1,figure.getPlayernumber());
    }

    @Test
    void getX() {
        figure.setX(1);
        assertEquals(1,figure.getX());
    }

    @Test
    void setX() {
        figure.setX(1);
        assertEquals(1,figure.getX());
    }

    @Test
    void getY() {
        figure.setY(1);
        assertEquals(1,figure.getY());
    }

    @Test
    void setY() {
        figure.setY(1);
        assertEquals(1,figure.getY());
    }

    @Test
    void getWidth() {
        figure.setWidth(1);
        assertEquals(1,figure.getWidth());
    }

    @Test
    void setWidth() {
        figure.setWidth(1);
        assertEquals(1,figure.getWidth());
    }

    @Test
    void createFigure() {
        figure.createFigure(relativeLayout,color);
        Mockito.verify(figure).createFigure(relativeLayout,color);
    }
}