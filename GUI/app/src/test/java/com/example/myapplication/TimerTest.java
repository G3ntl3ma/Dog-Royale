package com.example.myapplication;

import android.os.CountDownTimer;

import android.os.CountDownTimer;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.mockito.Mockito.*;

public class TimerTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TimerviewModel viewModel;
    private CountDownTimer countDownTimer;

    private Timer timer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        timer = new Timer(60000);
        timer.viewModel = viewModel;
    }

    @Test
    public void testStartTimer() throws InterruptedException {
        // Given
        CountDownLatch latch = new CountDownLatch(1);

        doAnswer(invocation -> {
            // Simulate onTick
            timer.updateCountdown();
            return null;
        }).when(viewModel).setTime(anyString());

        // When
        timer.startTimer();

        // Wait for one tick to occur
        latch.await(2, TimeUnit.SECONDS);

        // Then
        verify(viewModel, times(1)).setTime(anyString());
    }

    @Test
    public void testPauseTimer() throws InterruptedException {
        // Given
        CountDownLatch latch = new CountDownLatch(1);

        doAnswer(invocation -> {
            // Simulate onTick
            timer.updateCountdown();
            return null;
        }).when(viewModel).setTime(anyString());

        timer.startTimer();

        // Wait for one tick to occur
        latch.await(2, TimeUnit.SECONDS);

        // When
        timer.pauseTimer();

        // Wait for a short time
        Thread.sleep(500);

        // Then
        verify(viewModel, times(1)).setTime(anyString());
    }

    @Test
    public void testResetTimer() throws InterruptedException {
        // Given
        CountDownLatch latch = new CountDownLatch(1);

        doAnswer(invocation -> {
            // Simulate onTick
            timer.updateCountdown();
            return null;
        }).when(viewModel).setTime(anyString());

        timer.startTimer();

        // Wait for one tick to occur
        latch.await(2, TimeUnit.SECONDS);

        // When
        timer.resetTimer();

        // Wait for a short time
        Thread.sleep(500);

        // Then
        verify(viewModel, times(2)).setTime(anyString());
    }
}
