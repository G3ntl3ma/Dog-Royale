package com.example.myapplication;


import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Test;

class WaitingScreenTest {
    @Test
    public void testNavigation_WaitingScreen() {
        FragmentScenario<StartingGames> waitingFragmentScenario = FragmentScenario.launchInContainer(StartingGames.class);
        Espresso.onView(ViewMatchers.withId(R.id.continuebutton)).perform(ViewActions.click());
        waitingFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.gaBo_const_l);
        });
    }
    @Test
    public void testWaitingScreenLifeCycle(){
        FragmentScenario<WaitingScreen> scenario = FragmentScenario.launchInContainer(WaitingScreen.class);
        scenario.onFragment(fragment -> {
            assertTrue(fragment.getView() != null);
        });
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }
}