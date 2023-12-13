package com.example.DogRoyalClient;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class TimerTest {
    @Mock
    private Timer timer;
    long l = 10L;
    long StarttimeMillis=20L;
    long millis = 30L;
    @Test
    public void startTimer() {
        timer.startTimer();
        Mockito.verify(timer).startTimer();
    }

    @Test
    public void pauseTimer() {
        timer.pauseTimer();
        Mockito.verify(timer).pauseTimer();
    }

    @Test
    public void resetTimer() {
        timer.resetTimer();
        Mockito.verify(timer).resetTimer();
    }

    @Test
    public void isTimerRunning() {
        timer.startTimer();
        assertEquals(true,timer.isTimerRunning());
        timer.pauseTimer();
        assertEquals(false,timer.isTimerRunning());
        timer.pauseTimer();
        assertEquals(false,timer.isTimerRunning());
    }

    @Test
    public void getTimeLeftMillis() {
        timer.startTimer();
        assertEquals(l,timer.getTimeLeftMillis());
        timer.resetTimer();
        assertEquals(StarttimeMillis,timer.getTimeLeftMillis());


    }

    @Test
    public void setStartTimeMillis() {
        timer.setStartTimeMillis(millis);
        Mockito.verify(timer).setStartTimeMillis(millis);
    }
}