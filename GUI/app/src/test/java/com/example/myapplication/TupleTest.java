package com.example.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class TupleTest {

    @Test
    public void testToString() {
        double x = 200.0;
        double y = 100.0;
        String expect_test_tostring = "(" + x + ", " + y + ")";
        Tuple methodtest1 = new Tuple(x,y);
        String result = methodtest1.toString();
        assertEquals(expect_test_tostring,result);
    }

    @Test
    public void getX() {
        double x = 10.0;
        double y = 20.0;
        double expect_x = 10.0;
        Tuple methodtest2 = new Tuple(x,y);
        double result = methodtest2.getX();
        assertEquals(expect_x,result,0);
    }

    @Test
    public void getY() {
        double x = 10.0;
        double y = 20.0;
        double expect_y = 20.0;
        Tuple methodtest2 = new Tuple(x,y);
        double result = methodtest2.getY();
        assertEquals(expect_y,result,0);
    }

    @Test
    public void vek_length() {
        double x = 30.0;
        double y = 40.0;
        double expect_length = 50.0;
        Tuple methodtest3 = new Tuple(x,y);
        double result = methodtest3.vek_length();
        assertEquals(expect_length,result,0);
    }
}