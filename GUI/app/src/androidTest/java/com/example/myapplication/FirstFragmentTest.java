package com.example.myapplication;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.*;

import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
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
        //start FragmentScenario
        FragmentScenario<FirstFragment> startFragmentScenario = FragmentScenario.launchInContainer(FirstFragment.class);
        //zu sicher aller View
        Espresso.onView(ViewMatchers.withId(R.id.main_to_current)).check(ViewAssertions.matches(isDisplayed()));
        //runing Click
        Espresso.onView(ViewMatchers.withId(R.id.main_to_current)).perform(ViewActions.click());
        //warten für Navigate fertig(Wie eine GPS fertig machen)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //besichtigen the Ziel von Navigate
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.SpectateGames);
            //schließ Scenario
            startFragmentScenario.close();
        });
    }

    @Test
    public void testNavigation_stating_game() {
        //start FragmentScenario
        FragmentScenario<FirstFragment> startFragmentScenario = FragmentScenario.launchInContainer(FirstFragment.class);
        //zu sicher aller View
        Espresso.onView(ViewMatchers.withId(R.id.action_Main_to_upcomming)).check(ViewAssertions.matches(isDisplayed()));
        //runing Click
        Espresso.onView(ViewMatchers.withId(R.id.action_Main_to_upcomming)).perform(ViewActions.click());
        //warten für Navigate fertig(Wie eine GPS fertig machen)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //besichtigen the Ziel von Navigate
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.StartingGames);
            //schließ Scenario
            startFragmentScenario.close();
        });
    }

    @Test
    public void testNavigation_match_histroy() {
        //start FragmentScenario
        FragmentScenario<FirstFragment> startFragmentScenario = FragmentScenario.launchInContainer(FirstFragment.class);
        //zu sicher aller View
        Espresso.onView(ViewMatchers.withId(R.id.action_FirstFragment_to_matchHistory)).check(ViewAssertions.matches(isDisplayed()));
        //runing Click
        Espresso.onView(ViewMatchers.withId(R.id.action_FirstFragment_to_matchHistory)).perform(ViewActions.click());
        //warten für Navigate fertig(Wie eine GPS fertig machen)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //besichtigen the Ziel von Navigate
        startFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.matchhistory);
            //schließ Scenario
            startFragmentScenario.close();
        });
    }
    @Test
    public void testFirstFragmentLifeCycle(){
        //start Scenario
        FragmentScenario<FirstFragment> scenario = FragmentScenario.launchInContainer(FirstFragment.class);
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