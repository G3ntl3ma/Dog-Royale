package com.example.DogRoyalClient;

import static org.junit.jupiter.api.Assertions.*;

import androidx.lifecycle.MutableLiveData;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

class ServerViewModelTest {
    @Mock
    private ServerViewModel serverViewModel;
    private List<Game> gamesList;
    private MutableLiveData<List<Game>> games;
    private Game game;
    private int pos;
    private int players;

    @Test
    void addGame() {
        serverViewModel.addGame(game);
        Mockito.verify(serverViewModel).addGame(game);
    }

    @Test
    void getGames() {
        serverViewModel.getGames();
        assertEquals(gamesList,serverViewModel.getGames());
    }

    @Test
    void removeGame() {
        serverViewModel.removeGame(pos);
        Mockito.verify(serverViewModel).removeGame(pos);
    }

    @Test
    void changeCurrentPlayers() {
        serverViewModel.changeCurrentPlayers(pos,players);
        Mockito.verify(serverViewModel).changeCurrentPlayers(pos,players);
    }
}