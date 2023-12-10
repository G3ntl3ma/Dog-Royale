package com.example.myapplication;

import static org.junit.Assert.*;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(AndroidJUnit4.class)
public class Game_boardTest {
    @Test
    public void GameboardLifeCycle(){
        FragmentScenario<Game_board> scenario = FragmentScenario.launchInContainer(Game_board.class);
        scenario.onFragment(fragment -> {
            assertTrue(fragment.getView() != null);
        });
        scenario.moveToState(Lifecycle.State.DESTROYED);
        scenario.onFragment(fragment -> {
            assertTrue(fragment.getView() == null);
        });
    }
    @Test
    
}