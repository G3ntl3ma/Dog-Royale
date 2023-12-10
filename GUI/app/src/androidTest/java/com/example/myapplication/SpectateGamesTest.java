package com.example.myapplication;

import static org.junit.Assert.*;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SpectateGamesTest {
    @Test
    public void testNavigation_Spectategames_menü() {
        FragmentScenario<SpectateGames> startFragmentScenario = FragmentScenario.launchInContainer(SpectateGames.class);
        Espresso.onView(ViewMatchers.withId(R.id.current_back_to_main)).perform(ViewActions.click());
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.FirstFragment);
        });
    }
    @Test
    public void testNavigation_Spectategames_Gameboard() {
        FragmentScenario<SpectateGames> startFragmentScenario = FragmentScenario.launchInContainer(SpectateGames.class);
        Espresso.onView(ViewMatchers.withId(R.id.spectateGameButton)).perform(ViewActions.click());
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.gaBo_const_l);
        });
    }
    @Test
    public void testSpectateGamesLifeCycle(){
        FragmentScenario<SpectateGames> scenario = FragmentScenario.launchInContainer(SpectateGames.class);
        scenario.onFragment(fragment -> {
            assertTrue(fragment.getView() != null);
        });
        scenario.moveToState(Lifecycle.State.DESTROYED);
        scenario.onFragment(fragment -> {
            assertTrue(fragment.getView() == null);
        });
    }
}