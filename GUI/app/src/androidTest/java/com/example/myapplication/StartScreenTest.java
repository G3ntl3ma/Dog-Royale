package com.example.myapplication;



import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
class StartScreenTest {
    @Test
    public void testedittext_startscreen() {
        FragmentScenario<StartScreen> startscreenfragmentScenario = FragmentScenario.launchInContainer(StartScreen.class);
        Espresso.onView(withId(R.id.editTextText)).perform(ViewActions.typeText("Enter a Name"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.editTextText)).check(matches(withText("Enter a Name")));
        startscreenfragmentScenario.close()
    }

    ;

    @Test
    public void testNavigation_match_histroy() {
        FragmentScenario<FirstFragment> startscreenFragmentScenario = FragmentScenario.launchInContainer(FirstFragment.class);
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click());
        startscreenFragmentScenario.onFragment(fragment -> {
            NavController navController = Navigation.findNavController(fragment.requireView());
            org.junit.Assert.assertEquals(navController.getCurrentDestination().getId(), R.id.FirstFragment);
        });
    }
    }
}