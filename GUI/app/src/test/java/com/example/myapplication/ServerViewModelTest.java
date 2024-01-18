package com.example.myapplication;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ServerViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ServerViewModel serverViewModel;
    private int[] c;
    private int[] b;
    @Before
    public void setup() {
        serverViewModel = new ServerViewModel();
    }

    @Test
    public void testAddGame() {
        Game mockGame = new Game("exampleID", 120, 2, 4, 5, 10,new int[]{1, 2, 3}, new int[]{4, 5, 6});
        serverViewModel.addGame(mockGame);
        List<Game> gamesList = serverViewModel.getGames().getValue();
        assertEquals(1, gamesList.size());
        assertEquals(mockGame, gamesList.get(0));
    }

    @Test
    public void testRemoveGame() {
        Game mockGame = new Game("exampleID", 120, 2, 4, 5, 10,new int[]{1, 2, 3}, new int[]{4, 5, 6});
        serverViewModel.addGame(mockGame);

        int initialSize = serverViewModel.getGames().getValue().size();
        serverViewModel.removeGame(0);

        List<Game> gamesList = serverViewModel.getGames().getValue();
        assertEquals(initialSize - 1, gamesList.size());
    }

    @Test
    public void testChangeCurrentPlayers() {
        Game mockGame = new Game("exampleID", 120, 2, 4, 5, 10,new int[]{1, 2, 3}, new int[]{4, 5, 6});
        serverViewModel.addGame(mockGame);

        int newPos = 0;
        int newPlayers = 5;

        serverViewModel.changeCurrentPlayers(newPos, newPlayers);

        List<Game> gamesList = serverViewModel.getGames().getValue();
        assertEquals(newPlayers, gamesList.get(newPos).getCurrentPlayers());
    }
}