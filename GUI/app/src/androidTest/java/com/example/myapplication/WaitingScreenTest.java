package com.example.myapplication;


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
    FragmentScenario<StartingGames> waitingFragmentScenario = FragmentScenario.launchInContainer(StartingGames.class);
        Espresso.onView(ViewMatchers.withId(R.id.continuebutton)).perform(ViewActions.click());
        waitingFragmentScenario.onFragment(fragment -> {
        NavController navController = Navigation.findNavController(fragment.requireView());
        org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.gaBo_const_l);
    });

}