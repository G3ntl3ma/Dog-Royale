package com.example.myapplication;

public class viewModelTest {
    ServerViewModel viewModel;

    viewModelTest(){
        viewModel = MainActivity.getServerViewModel();
        viewModel.addGame(new Game("Game 1", 60, 4, 6, 20, 20));
        viewModel.addGame(new Game("Game 2", 30, 3, 4, 200, 9));
        viewModel.addGame(new Game("Game 3", 300, 1, 6, 6, 2));

        viewModel.removeGame(0);
        viewModel.changeCurrentPlayers(1, 4);
    }
}
