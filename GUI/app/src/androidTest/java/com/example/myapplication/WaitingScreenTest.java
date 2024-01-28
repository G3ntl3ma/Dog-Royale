package com.example.myapplication;


import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Test;

class WaitingScreenTest {
    @Test
    public void testNavigation_WaitingScreen() {
        //start FragmentScenario
        FragmentScenario<StartingGames> waitingFragmentScenario = FragmentScenario.launchInContainer(StartingGames.class);
        //runing typetext
        Espresso.onView(ViewMatchers.withId(R.id.continuebutton)).check(ViewAssertions.matches(isDisplayed()));
        //stop Eingeben
        Espresso.onView(ViewMatchers.withId(R.id.continuebutton)).perform(ViewActions.click());
        //besichertigen ob gleich wie wir eingegeben haben
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //besichtigen the Ziel von Navigate
        waitingFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.gaBo_const_l);
            //schließ Scenario
            waitingFragmentScenario.close();
        });
    }
    @Test
    public void testWaitingScreenLifeCycle(){
        //start Scenario
        FragmentScenario<WaitingScreen> scenario = FragmentScenario.launchInContainer(WaitingScreen.class);
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