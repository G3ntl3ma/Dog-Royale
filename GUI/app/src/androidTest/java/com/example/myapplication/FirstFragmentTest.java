package com.example.myapplication;

import static org.junit.Assert.*;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.fragment.app.testing.FragmentScenario;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FirstFragmentTest {
    @Test
    public void testNavigation_specate_game() {
        FragmentScenario<FirstFragment> startFragmentScenario = FragmentScenario.launchInContainer(FirstFragment.class);
        Espresso.onView(ViewMatchers.withId(R.id.main_to_current)).perform(ViewActions.click());
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.SpectateGames);
        });
    }

    @Test
    public void testNavigation_stating_game() {
        FragmentScenario<FirstFragment> startFragmentScenario = FragmentScenario.launchInContainer(FirstFragment.class);
        Espresso.onView(ViewMatchers.withId(R.id.action_Main_to_upcomming)).perform(ViewActions.click());
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.StartingGames);
        });
    }

    @Test
    public void testNavigation_match_histroy() {
        FragmentScenario<FirstFragment> startFragmentScenario = FragmentScenario.launchInContainer(FirstFragment.class);
        Espresso.onView(ViewMatchers.withId(R.id.action_FirstFragment_to_matchHistory)).perform(ViewActions.click());
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.matchhistory);
        });
    }


}