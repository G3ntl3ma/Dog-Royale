package com.example.myapplication;



import android.app.ActionBar;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

//for display size
import android.util.DisplayMetrics;

import androidx.fragment.app.Fragment;


import android.view.Display;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;


import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    static ServerViewModel serverViewModel;
    static GameboardViewModel gameboardViewModel;
    static CurrentGameViewModel currentGameViewModel;
    static TimerviewModel timerviewModel;
    static LastCardViewModel lastCardViewModel;
    static DiscardPileViewModel discardPileViewModel;
    static StartingGamesViewmodel startingGamesViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        //Set up all Viewmodels
        serverViewModel = new ViewModelProvider(this).get(ServerViewModel.class);
        gameboardViewModel = new ViewModelProvider(this).get(GameboardViewModel.class);
        currentGameViewModel = new ViewModelProvider(this).get(CurrentGameViewModel.class);
        timerviewModel = new ViewModelProvider(this).get(TimerviewModel.class);
        lastCardViewModel = new ViewModelProvider(this).get(LastCardViewModel.class);
        discardPileViewModel = new ViewModelProvider(this).get(DiscardPileViewModel.class);
        startingGamesViewmodel = new ViewModelProvider(this).get(StartingGamesViewmodel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static ServerViewModel getServerViewModel(){
        return serverViewModel;
    }
    public static GameboardViewModel getGameboardViewModel(){return gameboardViewModel;}
    public static CurrentGameViewModel getCurrentGameViewModel(){return currentGameViewModel;}
    public static TimerviewModel getTimerViewModel(){return timerviewModel;}
    public static LastCardViewModel getLastCardViewModel(){return lastCardViewModel;}
    public static DiscardPileViewModel getDiscardPileViewModel(){return discardPileViewModel;}
    public static StartingGamesViewmodel getStartingGamesViewmodel(){return startingGamesViewmodel;}
}