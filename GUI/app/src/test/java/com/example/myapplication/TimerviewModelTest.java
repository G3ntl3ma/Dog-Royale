package com.example.myapplication;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TimerviewModelTest {
    //to make sure that it can in main thread immediately run
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    //Mock a Observer to check that,ob we have recived the true TimeValue
    @Mock
    private Observer<String> observer;

    private TimerviewModel timerviewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        timerviewModel = new TimerviewModel();
        //use observer model
        timerviewModel.getTime().observeForever(observer);
    }

    @Test
    public void testSetTime() {
        // give a Entity
        String testTime = "12:43";

        // When
        timerviewModel.setTime(testTime);

        // Then
        Mockito.verify(observer).onChanged(testTime);
    }
}
