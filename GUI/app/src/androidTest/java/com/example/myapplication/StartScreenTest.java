package com.example.myapplication;



import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.junit.Assert.assertTrue;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
class StartScreenTest {
    @Test
    public void testedittext_startscreen() {
        //Start Scenario
        FragmentScenario<StartScreen> startscreenfragmentScenario = FragmentScenario.launchInContainer(StartScreen.class);
        //runing typetext
        Espresso.onView(ViewMatchers.withId(R.id.editTextText)).perform(ViewActions.typeText("Enter a Name"));
        //stop Eingeben
        Espresso.closeSoftKeyboard();
        //besichertigen ob gleich wie wir eingegeben haben
        Espresso.onView(ViewMatchers.withId(R.id.editTextText)).check(ViewAssertions.matches(ViewMatchers.withText("Enter a Name")));
        //schließ Scenario
        startscreenfragmentScenario.close();
    }

    ;

    @Test
    public void testNavigation_match_histroy() {
        //start FragmentScenario
        FragmentScenario<FirstFragment> startscreenFragmentScenario = FragmentScenario.launchInContainer(FirstFragment.class);
        //zu sicher aller View
        Espresso.onView(ViewMatchers.withId(R.id.button)).check(ViewAssertions.matches(isDisplayed()));
        //runing Click
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click());
        //warten für Navigate fertig(Wie eine GPS fertig machen)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //besichtigen the Ziel von Navigate
        startscreenFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.FirstFragment);
            //schließ Scenario
            startscreenFragmentScenario.close();
        });
    }
    @Test
    public void testStratScreenLifeCycle(){
        //start Scenario
        FragmentScenario<StartScreen> scenario = FragmentScenario.launchInContainer(StartScreen.class);
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