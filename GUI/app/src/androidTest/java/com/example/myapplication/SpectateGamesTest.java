package com.example.myapplication;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.*;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SpectateGamesTest {
    @Test
    public void testNavigation_Spectategames_menü() {
        //start FragmentScenario
        FragmentScenario<SpectateGames> startFragmentScenario = FragmentScenario.launchInContainer(SpectateGames.class);
        //zu sicher aller View
        Espresso.onView(ViewMatchers.withId(R.id.current_back_to_main)).check(ViewAssertions.matches(isDisplayed()));
        //runing Click
        Espresso.onView(ViewMatchers.withId(R.id.current_back_to_main)).perform(ViewActions.click());
        //warten für Navigate fertig(Wie eine GPS fertig machen)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //besichtigen the Ziel von Navigate
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.FirstFragment);
            //schließ Scenario
            startFragmentScenario.close();
        });
    }
    @Test
    public void testNavigation_Spectategames_Gameboard() {
        //start FragmentScenario
        FragmentScenario<SpectateGames> startFragmentScenario = FragmentScenario.launchInContainer(SpectateGames.class);
        //zu sicher aller View
        Espresso.onView(ViewMatchers.withId(R.id.spectateGameButton)).check(ViewAssertions.matches(isDisplayed()));
        //runing Click
        Espresso.onView(ViewMatchers.withId(R.id.spectateGameButton)).perform(ViewActions.click());
        //warten für Navigate fertig(Wie eine GPS fertig machen)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //besichtigen the Ziel von Navigate
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.gaBo_const_l);
            //schließ Scenario
            startFragmentScenario.close();
        });
    }
    @Test
    public void testSpectateGamesLifeCycle(){
        //start Scenario
        FragmentScenario<SpectateGames> scenario = FragmentScenario.launchInContainer(SpectateGames.class);
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