package com.example.myapplication;

public class viewModelTest {
    ServerViewModel viewModel;

    viewModelTest(){
        viewModel = MainActivity.getServerViewModel();
        viewModel.addGame(new Game("Game 1", 60, 4, 6, 20, 20, new int[]{0,1,2,3,5,6,7,8,10,11},new int[]{4,9,14,19}));
        viewModel.addGame(new Game("Game 2", 30, 3, 4, 200, 9, new int[]{101,102,103,106}, new int[]{49, 99, 149}));
        viewModel.addGame(new Game("Game 3", 300, 1, 6, 6, 2, new int[]{0,2,4}, new int[]{0}));

        viewModel.removeGame(0);
        viewModel.changeCurrentPlayers(1, 4);
    }
}
