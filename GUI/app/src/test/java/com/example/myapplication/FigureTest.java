package com.example.myapplication;

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
    void createFigure() {
        figure.createFigure(relativeLayout,color);
        Mockito.verify(figure).createFigure(relativeLayout,color);
    }
}