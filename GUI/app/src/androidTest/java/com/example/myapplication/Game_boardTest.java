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
        //start Scenario
        FragmentScenario<Game_board> scenario = FragmentScenario.launchInContainer(Game_board.class);
        //verify on CREATED zustand
        scenario.onFragment(fragment -> {
            assertTrue(fragment.getView() != null);
        });
        //changed zustand to DESTROYED
        scenario.moveToState(Lifecycle.State.DESTROYED);
        //verify on DESTROYED zustand
        scenario.onFragment(fragment -> {
            assertTrue(fragment.getView() == null);
        });
    }
}