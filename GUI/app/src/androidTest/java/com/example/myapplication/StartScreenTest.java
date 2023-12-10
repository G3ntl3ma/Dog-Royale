package com.example.myapplication;



import org.junit.Test;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

class StartScreenTest {
    @Test
    FragmentScenario<StartScreen> fragmentScenario = FragmentScenario.launchInContainer(StartScreen.class);
    Espresso.
}