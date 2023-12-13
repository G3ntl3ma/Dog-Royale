package com.example.DogRoyalClient;

import static org.junit.Assert.*;

import org.junit.Test;

public class PolarCoordinateTest {
    //we test ToString method here
    @Test
    public void testToString() {
        int radiustest = 50;
        double degreetest = 45.0;
        String expect_test_tostring ="(" + radiustest + ", " + degreetest + ")";
        PolarCoordinate methodtest1 = new PolarCoordinate(radiustest,degreetest);
        String result = methodtest1.toString();
        assertEquals(expect_test_tostring,result);
    }

    @Test
    public void toTuple() {
        int radiustest = 1;
        double degreetest = 5.0;
        PolarCoordinate methodtest2 = new PolarCoordinate(radiustest,degreetest);
        Tuple expected_test_tuple =new Tuple (1.0,5.0);
        Tuple result = methodtest2.toTuple();
        assertEquals(expected_test_tuple.getX(),result.getX(),0);
        assertEquals(expected_test_tuple.getY(),result.getY(),0);

    }
}