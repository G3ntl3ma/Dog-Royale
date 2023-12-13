package com.example.DogRoyalClient;



import android.os.Bundle;

import com.example.DogRoyalClient.controller.ClientController;

import androidx.appcompat.app.AppCompatActivity;

//for display size


import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.DogRoyalClient.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    ClientController clientController = ClientController.getInstance();

    static ServerViewModel serverViewModel;
    static GameboardViewModel gameboardViewModel;
    static CurrentGameViewModel currentGameViewModel;
    static TimerviewModel timerviewModel;
    static LastCardViewModel lastCardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientController = ClientController.getInstance();
        clientController.startClient();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        serverViewModel = new ViewModelProvider(this).get(ServerViewModel.class);
        gameboardViewModel = new ViewModelProvider(this).get(GameboardViewModel.class);
        currentGameViewModel = new ViewModelProvider(this).get(CurrentGameViewModel.class);
        timerviewModel = new ViewModelProvider(this).get(TimerviewModel.class);
        lastCardViewModel = new ViewModelProvider(this).get(LastCardViewModel.class);
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
}