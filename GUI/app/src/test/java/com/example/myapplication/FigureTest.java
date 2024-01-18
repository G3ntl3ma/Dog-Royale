package com.example.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

import android.widget.RelativeLayout;
import android.content.Context;
import android.widget.ImageView;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FigureTest {
    @Mock
    private Context context;
    private RelativeLayout relativeLayout;
    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        relativeLayout = new RelativeLayout(context);
    }

    @Test
    public void createFiguretest() {
        int testColor = android.R.color.holo_blue_light; // Replace with your desired color resource

        // Mock figure properties
        int width = 100;
        int x = 50;
        int y = 50;
        int playernumber = 1;
        int id = 1;
        Figure figure = new Figure(width, x, y, playernumber, id);//create an entity
        figure.createFigure(relativeLayout,testColor);//call the method
        ImageView createdFigure = relativeLayout.findViewWithTag("figure" + playernumber + "_" + id);//retriev the created ImageView from the layout
        Assertions.assertNotNull(createdFigure);//to make sure that,we have not make a empty ImageView
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) createdFigure.getLayoutParams();
        Assertions.assertEquals(width, layoutParams.width);
        Assertions.assertEquals(width, layoutParams.height);
        Assertions.assertEquals(x, layoutParams.leftMargin);
        Assertions.assertEquals(y, layoutParams.topMargin);
    }
}