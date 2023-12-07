package com.example.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoordinateCalculatorTest {

    @Test
    public void getFieldsize() {
        int fieldsize = 100;
        int playercount = 20;
        int radius = 10;
        int expect_filedsize = 100;
        CoordinateCalculator methodtest = new CoordinateCalculator(fieldsize,playercount,radius);
        int result = methodtest.getFieldsize();
        assertEquals(expect_filedsize,result,0);
    }

    @Test
    public void getradius() {
        int fieldsize = 100;
        int playercount = 20;
        int radius = 10;
        int expect_radius = 10;
        CoordinateCalculator methodtest2 = new CoordinateCalculator(fieldsize,playercount,radius);
        int result = methodtest2.getradius();
        assertEquals(expect_radius,result,0);
    }

    @Test
    public void getPlayerCount() {
        int fieldsize = 100;
        int playercount = 20;
        int radius = 10;
        int expect_playercount = 100;
        CoordinateCalculator methodtest3 = new CoordinateCalculator(fieldsize,playercount,radius);
        int result = methodtest3.getFieldsize();
        assertEquals(expect_playercount,result,0);
    }

    @Test
    public void calculateCoordinates() {
        int fieldsize = 200;
        int playercount = 4;
        int radius = 30;
        CoordinateCalculator methodtest4 = new CoordinateCalculator(fieldsize,playercount,radius);
        Tuple expect_coordinate = new Tuple(30,90/57.296);
        Tuple result = methodtest4.calculateCoordinates(50);
        assertEquals(expect_coordinate.getX(),result.getX(),0);
        assertEquals(expect_coordinate.getY(),result.getY(),0);
    }

    @Test
    public void calculateCartesicCoordinates() {
        int fieldsize = 200;
        int playercount = 4;
        int radius = 30;
        CoordinateCalculator methodtest5 = new CoordinateCalculator(fieldsize,playercount,radius);
        Tuple result = methodtest5.calculateCartesicCoordinates(50);
        Tuple expect_CartesicCoordinate= new Tuple(30*Math.cos(methodtest5.calculateCoordinates(50).getY()),30*Math.sin(methodtest5.calculateCoordinates(50).getY()));
        assertEquals(expect_CartesicCoordinate.getX(),result.getX(),0);
        assertEquals(expect_CartesicCoordinate.getY(),result.getY(),0);
    }

    @Test
    public void calculateFloatCoordinates() {
        int fieldsize = 200;
        int playercount = 4;
        int radius = 30;
        CoordinateCalculator methodtest6 = new CoordinateCalculator(fieldsize,playercount,radius);
        Tuple result = methodtest6.calculateFloatCoordinates(50);
        Tuple expect_floatCoordinates = new Tuple((float) methodtest6.calculateCartesicCoordinates(50).getX(),(float) methodtest6.calculateFloatCoordinates(50).getY());
        assertEquals(expect_floatCoordinates.getX(),result.getX(),0);
        assertEquals(expect_floatCoordinates.getY(),result.getY(),0);
    }
}